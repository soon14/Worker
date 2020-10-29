package com.xsd.jx.mine;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.ExperienceResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.databinding.ActivityResumeBinding;
import com.xsd.jx.databinding.ItemWorkHistoryBinding;
import com.xsd.jx.listener.OnWorkTypeSelectListener;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 个人简历
 */
public class ResumeActivity extends BaseBindBarActivity<ActivityResumeBinding> {
    private List<WorkTypeBean> workTypes;
//    private int workTypeId;//单选的工作类型id
    @Override
    protected int getLayoutId() {
        return R.layout.activity_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadExp();
    }
    //用户详情
    private void loadUserInfo() {
        dataProvider.user.info()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UserInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UserInfoResponse> baseResponse) {
                        UserInfoResponse data = baseResponse.getData();
                        UserUtils.saveUser(data);
                        db.setItem(data.getInfo());
                        workTypes = data.getWorkTypes();
                        setWorkTypes();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void loadExp() {
        dataProvider.user.experience(1)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<ExperienceResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<ExperienceResponse> baseResponse) {
                        ExperienceResponse data = baseResponse.getData();
                        List<ExperienceResponse.ItemsBean> items = data.getItems();
                        setExpData(items);
                    }
                });

    }


    private void initView() {
        tvTitle.setText("个人简历");
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.layout_edit:
                    goActivity(EditIntroActivity.class);
                    break;
                case R.id.tv_add_work_type:
//                    new XPopup.Builder(this)
//                            .asCustom(new BottomAddWorkTypePop(this, workTypes, new BottomAddWorkTypePop.OnAddWorkTypesListener() {
//                                @Override
//                                public void addWorkTypes(Set<WorkTypeBean> types) {
//                                    workTypes.addAll(types);
//                                    setWorkTypes();
//                                }
//                            }))
//                            .show();
                    PopShowUtils.showWorkTypeSelect(this, new OnWorkTypeSelectListener() {
                        @Override
                        public void onSelect(WorkTypeBean workTypeBean) {
//                            workTypeId = workTypeBean.getId();
                            submit(workTypeBean);
                        }
                    });
                    break;
                case R.id.tv_edit_intro:
                    new XPopup.Builder(this)
                            .asInputConfirm("请输入自我介绍", "", text -> editUserInfo(text)).show();
                    break;
            }
        });
    }
    //工种列表
    private void setWorkTypes() {
        db.layoutTypesWork.removeAllViews();
        for (int i = 0; i < workTypes.size(); i++) {
            WorkTypeBean workTypesBean = workTypes.get(i);
            View viewType = LayoutInflater.from(this).inflate(R.layout.item_type_work_del, null);
            ImageView ivDel = viewType.findViewById(R.id.iv_del);
            TextView tvName = viewType.findViewById(R.id.tv_name);
            tvName.setText(workTypesBean.getTitle());
            db.layoutTypesWork.addView(viewType);
            ivDel.setVisibility(View.GONE);
            ivDel.setOnClickListener(v -> showDelPop(workTypesBean,viewType));
        }
    }

    private void submit(WorkTypeBean workTypeBean) {
        StringBuilder builder = new StringBuilder();
        for (WorkTypeBean tag : workTypes) {
            int id = tag.getId();
            builder.append(id).append(",");
        }
        String s = builder.toString();
        String ids =s.substring(0,s.lastIndexOf(","));
        L.e("ids=="+ids);
        dataProvider.work.workTypeSubmitChoice(workTypeBean.getId()+"")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        db.layoutTypesWork.removeAllViews();
                        View viewType = LayoutInflater.from(ResumeActivity.this).inflate(R.layout.item_type_work_del, null);
                        ImageView ivDel = viewType.findViewById(R.id.iv_del);
                        TextView tvName = viewType.findViewById(R.id.tv_name);
                        tvName.setText(workTypeBean.getTitle());
                        ivDel.setVisibility(View.GONE);
                        db.layoutTypesWork.addView(viewType);
                    }
                });
    }
    //删除工种提醒弹框
    private void showDelPop(WorkTypeBean workTypesBean,View viewType){
        new XPopup.Builder(this)
                .asConfirm("提醒",
                        "您是否确定删除该项？",
                        "取消",
                        "确定",
                        () -> {
                           dataProvider.work.workTypeRem(workTypesBean.getId())
                                    .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                                        @Override
                                        protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                            workTypes.remove(workTypesBean);
                                            viewType.animate()
                                                    .setListener(new Animator.AnimatorListener() {
                                                        @Override
                                                        public void onAnimationStart(Animator animation) {
                                                        }

                                                        @Override
                                                        public void onAnimationEnd(Animator animation) {
                                                            db.layoutTypesWork.removeView(viewType);
                                                        }

                                                        @Override
                                                        public void onAnimationCancel(Animator animation) {

                                                        }

                                                        @Override
                                                        public void onAnimationRepeat(Animator animation) {

                                                        }
                                                    })
                                                    .translationX(ScreenUtils.getRealWidth())
                                                    .alpha(0f)
                                                    .start();
                                        }
                                    });
                        },
                        null,
                        false,R.layout.dialog_tips)
                .show();
    }
    //经验列表:如果工人没有工作经历，那么显示一句灰色的话：该工人还未在平台完成订单
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
    //编辑用户信息
    private void editUserInfo(String intro){
        Map<String, RequestBody> map = new HashMap<>();
        map.put("intro", RequestBody.create(MediaType.parse("text/plain"), intro));
        dataProvider.user.profile(map)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        db.tvIntro.setText(intro);
                    }
                });
    }
}