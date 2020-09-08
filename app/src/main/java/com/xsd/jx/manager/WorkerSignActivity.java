package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.View;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityWorkerSignBinding;

/**
 * 工人考勤
 */
public class WorkerSignActivity extends BaseBindBarActivity<ActivityWorkerSignBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.layoutSignList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(WorkerSignListActivity.class);
            }
        });
    }

    private void initView() {
        tvTitle.setText("工人考勤");
    }
}