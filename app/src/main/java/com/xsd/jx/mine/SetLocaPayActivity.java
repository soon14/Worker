package com.xsd.jx.mine;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.LocaAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.LocaResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

/**
 * 全部事业部
 * 设置事业部支付工资
 */
public class SetLocaPayActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private LocaAdapter mAdapter = new LocaAdapter();
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
        tvTitle.setText("全部事业部");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.recyclerView.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            mAdapter.addData(new LocaResponse());
        }

    }
}