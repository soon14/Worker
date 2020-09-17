package com.xsd.jx.order;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.base.BaseActivity;

/**
 * Date: 2020/9/16
 * author: SmallCake
 */
public interface OrderView {
    RecyclerView getRecyclerView();
    SwipeRefreshLayout getSwipeRefreshLayout();
    BaseActivity getBaseActivity();
    int getType();
}
