package com.xsd.jx.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersInfoAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.custom.WaitPayBillPop;
import com.xsd.jx.databinding.ActivityGetWorkersInfoBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;
import com.xsd.utils.ClipboardUtils;
import com.xsd.utils.ToastUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 招工详情:8个状态
 status 状态 -1:不展示(有预付款项未付不显示给用户) )
 1:正在招
 2:已招满/待开工(所有用户已确认)
 3:工期中
 4:待结算
 5:待评价
 6:已完成
 7:已取消
 8:已过期
 */
public class GetWorkersInfoActivity extends BaseBindBarActivity<ActivityGetWorkersInfoBinding> {
    private GetWorkersInfoAdapter mAdapter;
    private int workId;//当前详情的工种ID
    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        onEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    @Receive(EventStr.CLOSE_GET_WORKERSINFO_ACTIVITY)
    public void closePage(){
        finish();
    }

    private void onEvent() {
        //layoutHead为appbar中的一个控件
       AppBarUtils.setColorGrayWhite(db.appbar,db.tabLayout);
       db.setClicklistener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               switch (view.getId()){
                   case R.id.layout_need_pay:
                       showWaitPayBill();
                       break;
                   case R.id.tv_together_comment:
                       goActivity(TogetherCommentActivity.class);
                       break;
                   case R.id.tv_copy:
                       String s = db.tvSn.getText().toString();
                       ClipboardUtils.copy(s);
                       break;
                   case R.id.tv_cancel://取消招聘
                       cancelInvite();
                       break;
               }
           }
       });
       mAdapter.addChildClickViewIds(R.id.tv_cancel,R.id.tv_confirm,R.id.tv_look);
       mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
           @Override
           public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
               WorkerBean item = (WorkerBean) adapter.getItem(position);
               int userId = item.getUserId();
               switch (view.getId()){
                   case R.id.tv_look://查看工人简历
                       Intent intent = new Intent(GetWorkersInfoActivity.this, WorkerResumeActivity.class);
                       intent.putExtra("type",1);
                       intent.putExtra("userId",item.getUserId());
                       intent.putExtra("workId",workId);
                       intent.putExtra("status",item.getStatus());
                       startActivity(intent);
                       break;
                   case R.id.tv_cancel://婉拒
                       doJoinWork(userId,1,position);
                       break;
                   case R.id.tv_confirm://雇佣
                       doJoinWork(userId,2,position);
                       break;
               }
           }
       });

    }

    private void cancelInvite() {
        dataProvider.server.cancelWork(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                    }
                });
    }

    /**
     * 拒绝/雇佣报名用户
     * @param userId 报名用户ID
     * @param type 类型 1:拒绝 2:雇佣
     */
    private void doJoinWork(int userId,int type,int position){
        dataProvider.server.doJoinWorker(workId,userId,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mAdapter.removeAt(position);
                        if (mAdapter.getData().size()==0){
                            finish();
                        }
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                    }
                });
    }

    private void showWaitPayBill() {
        new XPopup.Builder(this)
                .atView(db.layoutNeedPay)
                .asCustom(new WaitPayBillPop(this))
                .show();
    }

    int type;
    private void initView() {
        MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) getIntent().getSerializableExtra("item");
        db.setItem(item);
        workId = item.getId();

        type = item.getItemType();
        mAdapter = new GetWorkersInfoAdapter();
        switch (type){
            case 1:
                db.tvTitle.setText("正在招");
                break;
            case 2:
                db.tvTitle.setText("已招满");
                db.layoutBtns.setVisibility(View.GONE);
                break;
            case 3:
                db.tvTitle.setText("工期内");
                break;
            case 4:
                db.tvTitle.setText("待结算");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.layoutNeedPay.setVisibility(View.VISIBLE);
                db.tvPay.setVisibility(View.VISIBLE);
                break;
            case 5:
                db.tvTitle.setText("待评价");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.tvTogetherComment.setVisibility(View.VISIBLE);
                break;
            case 6:
                db.tvTitle.setText("已完成");
                db.layoutPayedTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("已结清（3200元）");
                setTopColor();
                break;
            case 7:
                db.tvTitle.setText("已取消");
                db.layoutCancelTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("2成/1200元(已退至您的账户钱包)");
                setTopColor();
                break;
        }
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("工人列表","招工详情"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0:
                        db.recyclerView.setVisibility(View.VISIBLE);
                        db.layoutInfo.setVisibility(View.GONE);
                        break;
                    case 1:
                        db.recyclerView.setVisibility(View.GONE);
                        db.layoutInfo.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_noperson, null);
        emptyView.findViewById(R.id.tv_get_workers).setOnClickListener(view -> goActivity(PushGetWorkersActivity.class));
        mAdapter.setEmptyView(emptyView);
        //工人列表
        List<WorkerBean> workers = item.getWorkers();
        for (int i = 0; i < workers.size(); i++) workers.get(i).setType(type);
        mAdapter.setList(workers);

    }


    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
    }
}