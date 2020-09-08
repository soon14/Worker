package com.xsd.jx.manager;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersInfoAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

public class GetWorkersWaitCommentActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private GetWorkersInfoAdapter mAdapter = new GetWorkersInfoAdapter();
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
        tvTitle.setText("待评价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 4; i++) {
            mAdapter.addData(new WorkerResponse(4));
        }
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
                case 7:
                    break;
            }

        });
        mAdapter.addChildClickViewIds(R.id.tv_single_comment);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.tv_single_comment:
                    goActivity(SingleCommentActivity.class);
                    break;
            }
        });

    }
}