package com.xsd.jx.manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.jx.pop.ConfirmNumPop;
import com.xsd.jx.pop.InviteJobsPop;
import com.xsd.jx.databinding.ActivityWorkerResumeBinding;
import com.xsd.jx.databinding.ItemWorkHistoryBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * 工人简历
 WorkerBean item = (WorkerBean) adapter.getItem(position);
 Intent intent = new Intent(GetWorkersActivity.this, WorkerResumeActivity.class);
 intent.putExtra("type", 0);//【0邀请上工】【1婉拒和雇佣】
 intent.putExtra("wtId", wtId);
 intent.putExtra("item", item);
 startActivity(intent);
 */
public class WorkerResumeActivity extends BaseBindBarActivity<ActivityWorkerResumeBinding> {
    private int userId;
    private int wtId;
    private int workId;
    private int fid;
    private int defaultNum;
    private int status;//状态 1:未处理 2：已确认 3：已拒绝

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()) {
                case R.id.tv_invite://邀请上工
                    showInviteJobs();
                    break;
                case R.id.tv_refuse://婉拒
                    doJoinWork(1);
                    break;
                case R.id.tv_hire://雇佣
                    doJoinWork(2);
                    break;
            }
        });
    }

    private void loadData() {
        dataProvider.server.cv(userId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkerInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkerInfoResponse> baseResponse) {
                        WorkerInfoResponse data = baseResponse.getData();
                        db.setItem(data);
                        List<WorkTypeBean> workTypes = data.getWorkTypes();
                        List<ExperienceResponse.ItemsBean> experience = data.getExperience();
                        initWorkTypes(workTypes);
                        setExpData(experience);
                        String avatar = data.getAvatar();
                        if (!TextUtils.isEmpty(avatar))db.ivHead.setOnClickListener(v -> PopShowUtils.showPic(db.ivHead,avatar));
                    }
                });
    }

    /**
     * 查询当前发布者同工种有几条招工信息，如果有多条，弹出框选择上工地点
     */
    private void showInviteJobs() {
        PopShowUtils.showInviteNum(this,defaultNum, new ConfirmNumPop.ConfirmListener() {
            @Override
            public void onConfirmNum(int num) {
                dataProvider.server.invite(userId, wtId, workId,num,fid)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<JobListResponse>>() {
                            @Override
                            protected void onSuccess(BaseResponse<JobListResponse> baseResponse) {
                                JobListResponse data = baseResponse.getData();
                                List<JobListResponse.ItemsBean> items = data.getItems();
                                if (items != null) {
                                    InviteJobsPop inviteJobsPop = new InviteJobsPop(WorkerResumeActivity.this, items);
                                    inviteJobsPop.setListener(itemsBean -> {
                                        workId = itemsBean.getWorkId();
                                        showInviteJobs();
                                    });
                                    new XPopup.Builder(WorkerResumeActivity.this)
                                            .asCustom(inviteJobsPop)
                                            .show();

                                } else {
                                    try {
                                        ToastUtil.showLong(baseResponse.getData().getMessage());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                                }
                            }
                        });
            }
        });

    }

    /**
     * 拒绝/雇佣报名用户
     * @param type 类型 1:拒绝 2:雇佣
     */
    private void doJoinWork(int type){
        dataProvider.server.doJoinWorker(workId,userId,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.CLOSE_GET_WORKERSINFO_ACTIVITY);
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                    }
                });
    }


    private void initView() {
        tvTitle.setText("工人简历");
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);//0邀请上工1.报名工人列表进入，显示拒绝和邀请
        WorkerBean item = (WorkerBean) intent.getSerializableExtra("item");
        userId = item.getUserId();
        fid = item.getFid();
        defaultNum = item.getNum();
        status = item.getStatus();
        boolean invited = item.isInvited();
        DataBindingAdapter.isInvite(db.tvInvite,invited);
        if (type == 0) {//显示邀请上工
            db.tvInvite.setVisibility(View.VISIBLE);
            wtId = intent.getIntExtra("wtId", 0);
        } else if (type == 1) {
            db.tvRefuse.setVisibility(View.VISIBLE);
            db.tvHire.setVisibility(View.VISIBLE);
        }
        //状态 1:未处理 2：已确认 3：已拒绝
        if (status==2){
            db.layoutBottomBtns.setVisibility(View.GONE);
        }


    }
    //工种，目前改为单工种
    private void initWorkTypes(List<WorkTypeBean> workTypes) {
        if (workTypes == null || workTypes.size() == 0) return;
        for (int i = 0; i < workTypes.size(); i++) {
            WorkTypeBean workTypeBean = workTypes.get(i);
            View viewType = LayoutInflater.from(this).inflate(R.layout.item_type_work_del, null);
            ImageView ivDel = viewType.findViewById(R.id.iv_del);
            ivDel.setVisibility(View.GONE);
            TextView tvName = viewType.findViewById(R.id.tv_name);
            tvName.setText(workTypeBean.getTitle());
            db.layoutTypesWork.addView(viewType);
        }
    }

    //工作经历
    private void setExpData(List<ExperienceResponse.ItemsBean> items) {
        if (items==null||items.size()==0){
            TextView tv = new TextView(this);
            tv.setText("该工人还未在平台完成订单");
            int dp16 = DpPxUtils.dp2px(16);
            tv.setPadding(dp16,dp16,dp16,dp16);
            db.layoutWorks.addView(tv);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            ExperienceResponse.ItemsBean item = items.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_work_history, null);
            ItemWorkHistoryBinding bind = DataBindingUtil.bind(view);
            bind.setItem(item);
            bind.simpleRatingBar.setRating(item.getRate());
            db.layoutWorks.addView(view);
        }
    }
}