package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityCommentBinding;

/**
 * 评价
 */
public class CommentActivity extends BaseBindBarActivity<ActivityCommentBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("评价对方");
    }
}