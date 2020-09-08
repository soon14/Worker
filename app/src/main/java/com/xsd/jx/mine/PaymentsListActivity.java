package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.PaymentsAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.PaymentsResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

/**
 * 收支列表
 */
public class PaymentsListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private PaymentsAdapter mAdapter = new PaymentsAdapter();
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
        tvTitle.setText("收支明细");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.layoutRoot.addView(LayoutInflater.from(this).inflate(R.layout.header_payments_list, null),1);
        for (int i = 0; i < 20; i++) {
            mAdapter.addData(new PaymentsResponse());
        }
    }
}