package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.MessageAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.MessageResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.TabUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.Arrays;

public class MessageActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private MessageAdapter mAdapter = new MessageAdapter();
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
        tvTitle.setText("消息");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View view = LayoutInflater.from(this).inflate(R.layout.header_message_tab, null);
        MagicIndicator tabLayout = view.findViewById(R.id.tab_layout);
        TabUtils.setDefaultTab(this, tabLayout, Arrays.asList("系统消息","订单消息"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {

            }
        });
        db.layoutRoot.addView(view,1);
        for (int i = 0; i < 20; i++) {
            mAdapter.addData(new MessageResponse());
        }
    }
}