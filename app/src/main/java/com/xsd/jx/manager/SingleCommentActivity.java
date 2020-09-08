package com.xsd.jx.manager;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;

/**
 * 评价工人
 * 单独评价
 */
public class SingleCommentActivity extends BaseBindBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        tvTitle.setText("评价工人");

    }
}