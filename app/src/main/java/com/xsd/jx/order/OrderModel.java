package com.xsd.jx.order;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.inject.DataProvider;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.mine.CommentActivity;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/9/16
 * author: SmallCake
 * 只获取数据
 * 对于用户端，订单模块
 *
 */
public class OrderModel {
    private DataProvider dataProvider;
    private SwipeRefreshLayout refreshLayout;
    private int page=1;
    private OrderAdapter mAdapter = new OrderAdapter();
    private OrderView view;
    private BaseActivity activity;
    public OrderModel(OrderView view) {
        this.view = view;
        this.dataProvider = view.getDataProvider();
        this.activity = view.getBaseActivity();
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
        mAdapter.addChildClickViewIds(R.id.tv_order_comment,R.id.tv_cancel,R.id.tv_contact_us,R.id.tv_contact_employer);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderBean item = (OrderBean) adapter.getItem(position);
            switch (view.getId()){
                case R.id.tv_order_comment://工人评价雇主
                    Intent intent = new Intent(activity, CommentActivity.class);
                    intent.putExtra("item",item);
                    activity.startActivity(intent);
                    break;
                case R.id.tv_cancel://取消报名
                    PopShowUtils.showConfirm(activity, "您是否确定取消报名该工作？","取消","确定", () -> orderCancel(item.getId(),position));
                    break;
                case R.id.tv_contact_us://联系平台
                    PopShowUtils.callUs(activity);
                    break;
                case R.id.tv_contact_employer://联系雇主
                    MobileUtils.callPhone(activity,item.getEmployerPhone());
                    break;
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderBean item = (OrderBean) adapter.getItem(position);
                Intent intent = new Intent(activity, OrderInfoActivity.class);
                intent.putExtra("item",item);
                activity.startActivity(intent);

            }
        });
    }
    private void orderCancel( int id, int position) {
        dataProvider.order.cancel(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mAdapter.removeAt(position);
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
