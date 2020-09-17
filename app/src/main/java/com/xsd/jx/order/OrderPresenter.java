package com.xsd.jx.order;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.base.BaseActivity;

/**
 * Date: 2020/9/16
 * author: SmallCake
 * 控制器
 */
public class OrderPresenter {
    private OrderView view;
    public OrderPresenter(OrderView view) {
        this.view = view;
    }
    public void start(){
        int type = view.getType();
        BaseActivity baseActivity = view.getBaseActivity();
        RecyclerView recyclerView = view.getRecyclerView();
        SwipeRefreshLayout swipeRefreshLayout = view.getSwipeRefreshLayout();
        new OrderModel(baseActivity,type,recyclerView,swipeRefreshLayout).initView();
    }
}
