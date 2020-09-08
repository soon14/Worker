package com.xsd.jx.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.xsd.jx.R;

/**
 * Date: 2020/8/21
 * author: SmallCake
 * 邀请得工作
 */
public class InviteJobPop extends CenterPopupView {
    public InviteJobPop(@NonNull Context context) {
        super(context);
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
        for (int i = 0; i < 6; i++) {
            View viewChild = LayoutInflater.from(this.getContext()).inflate(R.layout.item_job, null);
            layoutContent.addView(viewChild);
        }

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
