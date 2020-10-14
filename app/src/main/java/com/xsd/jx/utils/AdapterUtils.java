package com.xsd.jx.utils;

import android.view.LayoutInflater;
import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.manager.PushGetWorkersActivity;
import com.xsd.utils.SmallUtils;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class AdapterUtils {
    public static void setEmptyDataView(BaseQuickAdapter adapter){
        adapter.setEmptyView(LayoutInflater.from(SmallUtils.getApp().getApplicationContext()).inflate(R.layout.empty_view_nodata,null));
    }
    public static void setNoPushView(BaseQuickAdapter adapter, BaseActivity activity){
        //没有发布
        View viewNoPersion = LayoutInflater.from(activity).inflate(R.layout.empty_view_no_push, null);
        viewNoPersion.setOnClickListener(v -> activity.goActivity(PushGetWorkersActivity.class));
        adapter.setEmptyView(viewNoPersion);
    }

    public static void onAdapterEvent(BaseQuickAdapter mAdapter, SwipeRefreshLayout refreshLayout, OnAdapterListener listener){
        setEmptyDataView(mAdapter);
        if (mAdapter instanceof LoadMoreModule)
        //加载更多
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            if (listener!=null)listener.loadMore();
        });
        //下拉刷新
        refreshLayout.setOnRefreshListener(() -> {
            if (listener!=null)listener.onRefresh();
        });
    }
}
