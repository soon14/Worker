package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.HelpRegistAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.HelpRegResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

/**
 * 帮注册列表
 */
public class HelpRegistListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private HelpRegistAdapter mAdapter = new HelpRegistAdapter();
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
        dataProvider.user.helpRegRecord(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<HelpRegResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<HelpRegResponse> baseResponse) {
                        HelpRegResponse data = baseResponse.getData();
                        List<HelpRegResponse.ItemsBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });

    }

    private void initView() {
        tvTitle.setText("帮注册记录");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.layoutRoot.addView(LayoutInflater.from(this).inflate(R.layout.header_help_regist_list,null),1);

    }
}