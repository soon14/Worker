package com.xsd.jx.job;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.custom.ConfirmNumPop;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;

import java.util.List;

/**
 *  0:不限 1:短期工 2：长期工
 */
public class PermanentWorkerActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private JobAdapter mAdapter = new JobAdapter();
    private int page;
    private int type;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        onEvent();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 1);
        tvTitle.setText(type==1?"突击工":"长期工");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);

    }
    private void onEvent() {
        mAdapter.addChildClickViewIds(R.id.tv_join);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            JobBean item = (JobBean) adapter.getItem(position);
            switch (view.getId()){
                case R.id.tv_join:
                    join(item.getId(),position);
                    break;
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            JobBean item = (JobBean) adapter.getItem(position);
            goJobInfoActivity(item.getId());
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
    private void join(int id,int position) {
        PopShowUtils.showJoinNum(this, new ConfirmNumPop.ConfirmListener() {
            @Override
            public void onConfirmNum(int num) {
                dataProvider.work.join(id,num)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                            @Override
                            protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                mAdapter.getData().get(position).setIsJoin(true);
                                mAdapter.notifyItemChanged(position);
                                PopShowUtils.showTips(PermanentWorkerActivity.this);
                            }
                        });
            }
        });

    }

    private void loadData() {
        dataProvider.work.list(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkListResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<WorkListResponse> baseResponse) {
                        WorkListResponse data = baseResponse.getData();
                        List<JobBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });
    }
}