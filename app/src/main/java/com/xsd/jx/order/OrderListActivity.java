package com.xsd.jx.order;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

/**
 * 包含
 * 1：待评价
 * 2.全部订单
 */
public class OrderListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> implements OrderView{
    private int type;//订单类型
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
        type = getIntent().getIntExtra("type", 0);
        tvTitle.setText(type==0?"全部订单":"待评价");
        new OrderPresenter(this);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return db.recyclerView;
    }
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return db.refreshLayout;
    }
    @Override
    public int getType() {
        return type;
    }
}