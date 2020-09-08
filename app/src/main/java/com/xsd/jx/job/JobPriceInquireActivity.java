package com.xsd.jx.job;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobSearchAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.databinding.ActivityJobPriceInquireBinding;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class JobPriceInquireActivity extends BaseBindBarActivity<ActivityJobPriceInquireBinding> {
    JobSearchAdapter mAdapter = new JobSearchAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_price_inquire;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String keyWord = v.getText().toString();
                    if (TextUtils.isEmpty(keyWord)){
                        ToastUtil.showLong("请输入搜索内容！");
                        return false;
                    }
                    //开始搜索keyWord相关内容
                }
                return false;
            }
        });
        db.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = db.etSearch.getText().toString();
                if (TextUtils.isEmpty(keyWord)){
                    ToastUtil.showLong("请输入搜索内容！");
                    return;
                }
            }
        });
    }

    private void initView() {
        tvTitle.setText("查工价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        List<JobSearchBean> datas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            datas.add(new JobSearchBean());
        }
        mAdapter.setList(datas);
    }
}