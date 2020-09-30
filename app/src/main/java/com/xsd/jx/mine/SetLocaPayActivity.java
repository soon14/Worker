package com.xsd.jx.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.LocaAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.DivisionBean;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;

import java.util.List;

/**
 * 全部事业部
 * 设置事业部支付工资
 */
public class SetLocaPayActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private LocaAdapter mAdapter = new LocaAdapter();
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
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DivisionBean item = (DivisionBean) adapter.getItem(position);
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("item",item);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        db.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        dataProvider.user.division()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<List<DivisionBean>>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<List<DivisionBean>> baseResponse) {
                        List<DivisionBean> data = baseResponse.getData();
                        mAdapter.setList(data);
                    }
                });
    }

    private void initView() {
        tvTitle.setText("全部事业部");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.recyclerView.setBackgroundColor(Color.WHITE);

    }
}