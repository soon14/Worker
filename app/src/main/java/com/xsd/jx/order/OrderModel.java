package com.xsd.jx.order;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.inject.DataProvider;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

/**
 * Date: 2020/9/16
 * author: SmallCake
 * 只获取数据
 */
public class OrderModel {
    private DataProvider dataProvider;
    private SwipeRefreshLayout refreshLayout;
    private int page=1;
    private OrderAdapter mAdapter = new OrderAdapter();
    private OrderView view;
    public OrderModel(OrderView view) {
        this.view = view;
        this.dataProvider = view.getDataProvider();
        this.refreshLayout = view.getSwipeRefreshLayout();
        RecyclerView recyclerView = view.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getRecyclerView().getContext()));
        recyclerView.setAdapter(mAdapter);
        page=1;

    }

    public void setPage(int page) {
        this.page = page;
    }

    public void initView(){
        loadData();
        AdapterUtils.onAdapterEvent(mAdapter, refreshLayout, new OnAdapterListener() {
            @Override
            public void loadMore() {
                page++;
                loadData();
            }
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }
        });
    }

    public void loadData() {
        dataProvider.order.list(page,view.getType())
                .subscribe(new OnSuccessAndFailListener<BaseResponse<OrderResponse>>(refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<OrderResponse> baseResponse) {
                        OrderResponse data = baseResponse.getData();
                        List<OrderBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            if (page==1){
                                mAdapter.setList(items);
                            }else
                            mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });
    }
}
