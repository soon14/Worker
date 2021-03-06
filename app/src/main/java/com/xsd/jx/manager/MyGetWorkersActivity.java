package com.xsd.jx.manager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.databinding.ActivityMyGetWorkersBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 【我的招工】 >> 招工详情{@link GetWorkersInfoActivity}
 */
public class MyGetWorkersActivity extends BaseBindBarActivity<ActivityMyGetWorkersBinding> {
    private GetWorkersAdapter mAdapter = new GetWorkersAdapter();
    private int page=1;
    private int type=1;//类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        onEvent();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    @Receive(EventStr.UPDATE_MY_GET_WORKERS)
    public void update(){
        page=1;
        loadData();
    }

    private void loadData() {
        dataProvider.server.workList(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MyGetWorkersResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<MyGetWorkersResponse> baseResponse) {
                        MyGetWorkersResponse data = baseResponse.getData();
                        List<MyGetWorkersResponse.ItemsBean> items = data.getItems();
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
        //类型 0:全部 1:正在招 2:已招满 3:工期内 4:待结算 5:待评价 6:已完成
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("正在招","待开工","工期内","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                type = position+1;
                page=1;
                loadData();
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);

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
        db.tvOrderComment.setOnClickListener(view -> goActivity(GetWorkersWaitCommentActivity.class));
        db.tvOrderAll.setOnClickListener(view -> goActivity(GetWorkersAllActivity.class));

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) adapter.getItem(position);
            goActivity(GetWorkersInfoActivity.class,item.getId());
        });

        mAdapter.addChildClickViewIds(R.id.tv_activ_get);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyGetWorkersResponse.ItemsBean item = (MyGetWorkersResponse.ItemsBean) adapter.getItem(position);
            switch (view.getId()){
                case R.id.tv_activ_get:
                    TextView tvActivGet = view.findViewById(R.id.tv_activ_get);
                    String s = tvActivGet.getText().toString();
                    if (s.equals("主动招人")){
                        finish();
                    }else {
                        goActivity(GetWorkersInfoActivity.class,item.getId());
                    }
                    break;
            }
        });
    }
}