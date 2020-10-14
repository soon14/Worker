package com.xsd.jx.manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.R;
import com.xsd.jx.adapter.MyWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.io.Serializable;
import java.util.List;

/**
 * 企业端
 * 我的招工-待评价页面
 */
public class GetWorkersWaitCommentActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private MyWorkersAdapter mAdapter = new MyWorkersAdapter();
    private int page=1;
    private int type=5;//类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        onEvent();
        loadData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }
    @Receive(EventStr.UPDATE_COMMENT_LIST)
    public void update(){
        page=1;
        loadData();
    }


    private void initView() {
        tvTitle.setText("待评价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);

    }
    private void onEvent() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) adapter.getItem(position);
            goActivity(GetWorkersInfoActivity.class,item.getId());
        });
        mAdapter.addChildClickViewIds(R.id.tv_comment_all);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) adapter.getItem(position);
            switch (view.getId()){
                case R.id.tv_comment_all://评价所有工人
                    Intent intent = new Intent(GetWorkersWaitCommentActivity.this, TogetherCommentActivity.class);
                    intent.putExtra("workId",item.getId());
                    intent.putExtra("workers", (Serializable) item.getWorkers());
                    startActivity(intent);
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