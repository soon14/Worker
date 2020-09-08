package com.xsd.jx.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ActivityCollectedWorksBinding;

/**
 * 收藏的工作
 */
public class CollectedWorksActivity extends BaseBindBarActivity<ActivityCollectedWorksBinding> {
    private JobAdapter mAdapter = new JobAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collected_works;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("收藏的工作");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 2; i++) {
            mAdapter.addData(new JobBean());
        }
    }
}