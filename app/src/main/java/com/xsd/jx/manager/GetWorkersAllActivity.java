package com.xsd.jx.manager;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.MyWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

public class GetWorkersAllActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private MyWorkersAdapter mAdapter = new MyWorkersAdapter();
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
    }
    private void onEvent() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            WorkerResponse item = (WorkerResponse) adapter.getItem(position);
            int itemType = item.getItemType();
            goActivity(GetWorkersInfoActivity.class,itemType);
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

            }

        });
    }
}