package com.xsd.jx.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

/**
 * 收藏的工作
 */
public class CollectedWorksActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private JobAdapter mAdapter = new JobAdapter();
    private int page=1;
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

    private void onEvent() {
        db.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }
        });
    }

    private void loadData() {
        dataProvider.user.favWorks(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<List<JobBean>>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<List<JobBean>> baseResponse) {
                        List<JobBean> data = baseResponse.getData();
                        mAdapter.setList(data);
                    }
                });
    }

    private void initView() {
        tvTitle.setText("收藏的工作");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        AdapterUtils.setEmptyDataView(mAdapter);

    }
}