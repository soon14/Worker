package com.xsd.jx.order;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

public class OrderAllActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private OrderAdapter mAdapter = new OrderAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }
    private void initView() {
        tvTitle.setText("全部订单");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 8; i++) {
            mAdapter.addData(new OrderResponse(i));
        }
    }
    private void onEvent() {
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
    }
}