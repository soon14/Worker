package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.AwardAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.AwardResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;

public class AwardActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private AwardAdapter mAdapter = new AwardAdapter();
    private int page=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        tvTitle.setText("奖励记录");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_award_list,null));
        for (int i = 0; i < 4; i++) {
            mAdapter.addData(new AwardResponse());
        }
        AdapterUtils.onAdapterEvent(mAdapter, db.refreshLayout, new OnAdapterListener() {
            @Override
            public void loadMore() {
                page++;

            }

            @Override
            public void onRefresh() {
                page=1;

            }
        });
    }
}