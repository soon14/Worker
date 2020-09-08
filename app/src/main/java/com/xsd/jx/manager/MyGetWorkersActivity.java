package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.MyWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ActivityMyGetWorkersBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.TabUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的招工
 */
public class MyGetWorkersActivity extends BaseBindBarActivity<ActivityMyGetWorkersBinding> {
    private MyWorkersAdapter mAdapter = new MyWorkersAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }
    private void initView() {
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("正在招","已招满","工期内","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                List<WorkerResponse> datas = new ArrayList<>();
                for (int i = 0; i < 20; i++) datas.add(new WorkerResponse(position));
                mAdapter.setList(datas);
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        List<WorkerResponse> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) datas.add(new WorkerResponse(0));
        mAdapter.setList(datas);
    }
    private void onEvent() {
        db.tvOrderComment.setOnClickListener(view -> goActivity(GetWorkersWaitCommentActivity.class));
        db.tvOrderAll.setOnClickListener(view -> goActivity(GetWorkersAllActivity.class));
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
        mAdapter.addChildClickViewIds(R.id.tv_order_comment);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_order_comment:
//                        goActivity(CommentActivity.class);
                        break;
                }
            }
        });
    }
}