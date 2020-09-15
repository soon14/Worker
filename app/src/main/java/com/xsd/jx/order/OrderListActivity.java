package com.xsd.jx.order;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.OrderUtils;

import java.util.List;

public class OrderListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private OrderAdapter mAdapter = new OrderAdapter();
    private int page=1;
    private int type;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        onEvent();
    }
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        tvTitle.setText(type==0?"全部订单":"待评价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        AdapterUtils.setEmptyDataView(mAdapter);

    }
    private void onEvent() {
        OrderUtils.onAdapterEvent(this, mAdapter, db.refreshLayout, new OnAdapterListener() {
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
    private void loadData() {
        dataProvider.order.list(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<OrderResponse>>(db.refreshLayout) {
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
                            }else mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });
    }
}