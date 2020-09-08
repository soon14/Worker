package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.WorkerSignListAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.databinding.ActivityWorkerSignListBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.TabUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 记录考勤
 */
public class WorkerSignListActivity extends BaseBindBarActivity<ActivityWorkerSignListBinding> {
    private WorkerSignListAdapter mAdapter = new WorkerSignListAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                goActivity(WorkerSignInfoActivity.class);
            }
        });
    }

    private void initView() {
        tvTitle.setText("考勤记录");
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("全部记录(5)","未考勤(2)","已考勤(3)"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0:
                        List<WorkerResponse> datas = new ArrayList<>();
                        datas.add(new WorkerResponse(0));
                        datas.add(new WorkerResponse(2));
                        datas.add(new WorkerResponse(1));
                        datas.add(new WorkerResponse(1));
                        mAdapter.setList(datas);
                    break;
                    case 1:
                        List<WorkerResponse> datas1 = new ArrayList<>();
                        datas1.add(new WorkerResponse(1));
                        datas1.add(new WorkerResponse(1));
                        mAdapter.setList(datas1);
                    break;
                    case 2:
                        List<WorkerResponse> datas2 = new ArrayList<>();
                        datas2.add(new WorkerResponse(0));
                        mAdapter.setList(datas2);
                    break;
                }
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        List<WorkerResponse> datas = new ArrayList<>();
        datas.add(new WorkerResponse(0));
        datas.add(new WorkerResponse(2));
        datas.add(new WorkerResponse(1));
        datas.add(new WorkerResponse(1));
        mAdapter.addData(datas);
    }
}