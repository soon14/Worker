package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.MessageAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageResponse;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.Arrays;
import java.util.List;

public class MessageActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private MessageAdapter mAdapter = new MessageAdapter();
    private int type=1;
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
        dataProvider.user.message(type,page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageResponse> baseResponse) {
                        MessageResponse data = baseResponse.getData();
                        List<MessageResponse.ItemsBean> items = data.getItems();
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
        tvTitle.setText("消息");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View view = LayoutInflater.from(this).inflate(R.layout.header_message_tab, null);
        MagicIndicator tabLayout = view.findViewById(R.id.tab_layout);
        TabUtils.setDefaultTab(this, tabLayout, Arrays.asList("系统消息","订单消息"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                type=position+1;
                page=1;
                loadData();
            }
        });
        db.layoutRoot.addView(view,1);
    }
}