package com.xsd.jx.job;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityJobInfoBinding;

/**
 * 详细招工信息
 */
public class JobInfoActivity extends BaseBindBarActivity<ActivityJobInfoBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
    }
}