package com.xsd.jx.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ItemJobBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/8/21
 * author: SmallCake
 * 邀请得工作
 */
public class InviteJobPop extends CenterPopupView {
    private List<JobBean> data;
    private BaseActivity activity;

    public InviteJobPop(@NonNull BaseActivity context, List<JobBean> data) {
        super(context);
        this.data = data;
        this.activity = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_invite_job;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        LinearLayout layoutContent = findViewById(R.id.layout_content);
        for (int i = 0; i < data.size(); i++) {
            JobBean jobBean = data.get(i);
            L.e("被邀请的ID=="+jobBean.getId());
            boolean isJoin = jobBean.isIsJoin();//是否已经报名
            View viewChild = LayoutInflater.from(this.getContext()).inflate(R.layout.item_job, null);
            ItemJobBinding bind = DataBindingUtil.bind(viewChild);
            bind.tvJoin.setText(isJoin ? "已接受邀请" : "接受上工邀请");
            bind.tvJoin.setBackgroundResource(isJoin ? R.drawable.round6_gray_bg : R.drawable.round6_blue_bg);
            bind.setItem(jobBean);
            layoutContent.addView(viewChild);
            //点击事件
            bind.tvJoin.setOnClickListener(view -> activity.getDataProvider().work.acceptInvite(jobBean.getId())
                    .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(activity.getDialog()) {
                        @Override
                        protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                            ToastUtil.showLong(baseResponse.getData().getMessage());
                            bind.tvJoin.setText("已接受邀请");
                            bind.tvJoin.setBackgroundResource(R.drawable.round6_gray_bg);
//                                    delayDismiss(800);
                            Apollo.emit(EventStr.UPDATE_INVITE_LIST);
                        }
                    }));
            viewChild.setOnClickListener(view -> activity.goJobInfoActivity(jobBean.getId()));
        }
        findViewById(R.id.layout_look_other).setOnClickListener(view -> dismiss());
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("以下" + data.size() + "位雇主正邀请您上工，点击接受直接上工");

    }

    @Override
    protected int getMaxWidth() {
        return (int) (XPopupUtils.getWindowWidth(getContext()) * 0.918f);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.918f);
    }
}
