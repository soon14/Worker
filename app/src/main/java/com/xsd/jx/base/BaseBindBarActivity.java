package com.xsd.jx.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xsd.jx.R;

/**
 * 子类必须引入<include layout="@layout/action_common_bar_bill"/>
 */
public abstract class BaseBindBarActivity<DB extends ViewDataBinding> extends BaseBindActivity<DB> {
    protected ImageView ivBack;
    protected TextView tvTitle;
    protected TextView tvRight;
    protected View line;
    protected View actionBarRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).statusBarDarkFont(true).init();
        actionBarRoot = findViewById(R.id.action_bar_root);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        line = findViewById(R.id.view_bar_line);
        ivBack.setOnClickListener(v -> finish());
    }
}
