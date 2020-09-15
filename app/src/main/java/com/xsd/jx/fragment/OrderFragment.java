package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.FragmentOrderBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.order.OrderListActivity;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.OrderUtils;
import com.xsd.jx.utils.TabUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 首页-订单
 */
public class OrderFragment extends BaseBindFragment<FragmentOrderBinding>{
    private static final String TAG = "OrderFragment";
    private OrderAdapter mAdapter = new OrderAdapter();
    private int page=1;
    private int type=1;//类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected void onLazyLoad() {
        initView();
        loadData();
        onEvent();
    }
    private void initView() {
        //类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
        TabUtils.setDefaultTab(this.getContext(), db.tabLayout,  Arrays.asList("报名中","待开工","工期中","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0: type=1;break;
                    case 1: type=2;break;
                    case 2: type=5;break;
                    case 3: type=6;break;
                }
                page=1;
                loadData();
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        db.recyclerView.setAdapter(mAdapter);
        AdapterUtils.setEmptyDataView(mAdapter);

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

    private void onEvent() {
        db.tvOrderComment.setOnClickListener(view -> goActivity(OrderListActivity.class,7));//待评价
        db.tvOrderAll.setOnClickListener(view -> goActivity(OrderListActivity.class,0));//0全部订单1待评价
        OrderUtils.onAdapterEvent((BaseActivity) this.getActivity(), mAdapter, db.refreshLayout, new OnAdapterListener() {
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


}
