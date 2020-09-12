package com.xsd.jx.order;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

public class OrderAllActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private OrderAdapter mAdapter = new OrderAdapter();
    private int page=1;
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
    private void loadData() {
        dataProvider.order.list(page,0)
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
    private void initView() {
        tvTitle.setText("全部订单");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view_nodata,null));

    }
    private void onEvent() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderBean item = (OrderBean) adapter.getItem(position);
            int itemType = item.getItemType();
            goActivity(OrderInfoActivity.class,itemType);
            switch (itemType){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        });

        //加载更多
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            page++;
            loadData();
        });

        //下拉刷新
        db.refreshLayout.setOnRefreshListener(() -> {
            page=1;
            loadData();
        });
    }
}