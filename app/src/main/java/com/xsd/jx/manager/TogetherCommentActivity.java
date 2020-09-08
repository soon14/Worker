package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityTogetherCommentBinding;

/**
 * 评价工人
 * 一起评价
 */
public class TogetherCommentActivity extends BaseBindBarActivity<ActivityTogetherCommentBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_together_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("评价工人");
        for (int i = 0; i < 3; i++) {
            View viewWorkers = LayoutInflater.from(this).inflate(R.layout.item_wait_comment_workers, null);
            db.layoutWorks.addView(viewWorkers);
        }
    }
}