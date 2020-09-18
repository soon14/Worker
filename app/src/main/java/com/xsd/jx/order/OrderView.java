package com.xsd.jx.order;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.base.BaseMvpCallback;

/**
 * Date: 2020/9/16
 * author: SmallCake
 */
public interface OrderView extends BaseMvpCallback {
    RecyclerView getRecyclerView();
    SwipeRefreshLayout getSwipeRefreshLayout();
    int getType();
}
