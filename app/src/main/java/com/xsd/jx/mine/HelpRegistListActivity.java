package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.HelpRegistAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.HelpUserResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;

/**
 * 帮注册列表
 */
public class HelpRegistListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private HelpRegistAdapter mAdapter = new HelpRegistAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("帮注册记录");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        db.layoutRoot.addView(LayoutInflater.from(this).inflate(R.layout.header_help_regist_list,null),1);
        for (int i = 0; i < 20; i++) {
            mAdapter.addData(new HelpUserResponse());
        }
    }
}