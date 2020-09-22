package com.xsd.jx.manager;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.MyWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ActivityMyGetWorkersBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;

import java.util.Arrays;

/**
 * 我的招工
 */
public class MyGetWorkersActivity extends BaseBindBarActivity<ActivityMyGetWorkersBinding> {
    private MyWorkersAdapter mAdapter = new MyWorkersAdapter();
    private int page=1;
    private int type=1;//类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void loadData() {
        dataProvider.server.workList(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MyGetWorkersResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MyGetWorkersResponse> baseResponse) {
                        MyGetWorkersResponse data = baseResponse.getData();

                    }
                });
    }

    private void initView() {
        //类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("正在招","已招满","工期内","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                type = position+1;
                page=1;
                loadData();
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);

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
        AdapterUtils.onAdapterEvent(mAdapter, db.refreshLayout, new OnAdapterListener() {
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