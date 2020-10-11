package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.PaymentsAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BalanceLogResponse;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

public class BalanceLogActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private PaymentsAdapter mAdapter = new PaymentsAdapter();
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
        dataProvider.user.balanceLog(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<BalanceLogResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<BalanceLogResponse> baseResponse) {
                        BalanceLogResponse data = baseResponse.getData();
                        List<BalanceLogResponse.ItemsBean> items = data.getItems();
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

    private void initView() {
        tvTitle.setText("收支明细");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.layoutRoot.addView(LayoutInflater.from(this).inflate(R.layout.header_payments_list, null),1);
    }
}