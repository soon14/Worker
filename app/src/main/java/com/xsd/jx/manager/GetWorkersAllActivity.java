package com.xsd.jx.manager;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.MyWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

public class GetWorkersAllActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private MyWorkersAdapter mAdapter = new MyWorkersAdapter();
    private int page=1;
    private int type=0;//类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }
    private void initView() {
        tvTitle.setText("全部招工");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
    }
    private void onEvent() {
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

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) adapter.getItem(position);
            goActivity(GetWorkersInfoActivity.class,item.getId());
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

    private void loadData() {
        dataProvider.server.workList(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MyGetWorkersResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<MyGetWorkersResponse> baseResponse) {
                        MyGetWorkersResponse data = baseResponse.getData();
                        List<MyGetWorkersResponse.ItemsBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            if (page==1)mAdapter.setList(items);else
                                mAdapter.getLoadMoreModule().loadMoreEnd();
                        }

                    }
                });
    }
}