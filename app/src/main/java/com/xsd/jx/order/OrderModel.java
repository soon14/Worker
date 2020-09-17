package com.xsd.jx.order;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.inject.DataProvider;
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
    private int type=1;
    private OrderAdapter mAdapter = new OrderAdapter();
    public OrderModel(BaseActivity activity, int type, RecyclerView recyclerView, SwipeRefreshLayout refreshLayout) {
        this.dataProvider = activity.getDataProvider();
        this.type = type;
        this.refreshLayout = refreshLayout;
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(mAdapter);
        AdapterUtils.setEmptyDataView(mAdapter);
        page=1;

    }
    public void initView(){
        loadData();
        //加载更多
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
           page++;
           loadData();
        });
        //下拉刷新
        refreshLayout.setOnRefreshListener(() -> {
            page=1;
            loadData();
        });
    }

    private void loadData() {
        dataProvider.order.list(page,type)
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
