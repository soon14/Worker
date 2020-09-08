package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.FragmentOrderBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.mine.CommentActivity;
import com.xsd.jx.order.OrderAllActivity;
import com.xsd.jx.order.OrderInfoActivity;
import com.xsd.jx.order.OrderWaitCommentActivity;
import com.xsd.jx.utils.TabUtils;

import java.util.ArrayList;
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
        onEvent();

    }
    private void onEvent() {
        db.tvOrderComment.setOnClickListener(view -> goActivity(OrderWaitCommentActivity.class));
        db.tvOrderAll.setOnClickListener(view -> goActivity(OrderAllActivity.class));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderResponse item = (OrderResponse) adapter.getItem(position);
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
        mAdapter.addChildClickViewIds(R.id.tv_order_comment);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_order_comment:
                        goActivity(CommentActivity.class);
                        break;
                }
            }
        });
    }

    private void initView() {
        TabUtils.setDefaultTab(this.getContext(), db.tabLayout,  Arrays.asList("报名中","待开工","工期中","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                List<OrderResponse> datas = new ArrayList<>();
                for (int i = 0; i < 20; i++) datas.add(new OrderResponse(position));
                mAdapter.setList(datas);
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        db.recyclerView.setAdapter(mAdapter);
        List<OrderResponse> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) datas.add(new OrderResponse(0));
        mAdapter.setList(datas);
    }
}
