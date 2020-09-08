package com.xsd.jx.job;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ActivityPermanentWorkerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 长期工
 */
public class PermanentWorkerActivity extends BaseBindBarActivity<ActivityPermanentWorkerBinding> {
    private JobAdapter mAdapter = new JobAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_permanent_worker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        int type = getIntent().getIntExtra("type", 0);
        tvTitle.setText(type==0?"突击工":"长期工");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        //添加头部提示

        List<JobBean> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new JobBean());
        }
        mAdapter.setList(datas);
    }
}