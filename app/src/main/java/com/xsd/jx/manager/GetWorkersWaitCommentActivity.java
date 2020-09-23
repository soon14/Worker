package com.xsd.jx.manager;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersInfoAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

public class GetWorkersWaitCommentActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private GetWorkersInfoAdapter mAdapter = new GetWorkersInfoAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }
    private void initView() {
        tvTitle.setText("待评价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);

    }
    private void onEvent() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {


        });
        mAdapter.addChildClickViewIds(R.id.tv_single_comment);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.tv_single_comment:
                    goActivity(SingleCommentActivity.class);
                    break;
            }
        });

    }
}