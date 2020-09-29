package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsd.jx.LoginActivity;
import com.xsd.jx.R;
import com.xsd.jx.adapter.JobFavAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * 收藏的工作
 *  status 状态 1:可报名 2:已过期
 */
public class CollectedWorksActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private JobFavAdapter mAdapter = new JobFavAdapter();
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
//        AdapterUtils.onAdapterEvent(mAdapter, db.refreshLayout, new OnAdapterListener() {
//            @Override
//            public void loadMore() {
//                page++;
//                loadData();
//            }
//            @Override
//            public void onRefresh() {
//                page=1;
//                loadData();
//
//            }
//        });
        mAdapter.addChildClickViewIds(R.id.tv_join);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (!UserUtils.isLogin()){
                    ToastUtil.showLong("请先登录！");
                    goActivity(LoginActivity.class);
                    return;
                }
                JobBean item = (JobBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.tv_join:
                        int status = item.getStatus();// 1:可报名 2:已过期
                        if (status==1)join(item.getId(),position);
                        else if (status==2)del(item.getId(),position);
                        break;
                }
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (!UserUtils.isLogin()){
                    ToastUtil.showLong("请先登录！");
                    goActivity(LoginActivity.class);
                    return;
                }
                JobBean item = (JobBean) adapter.getItem(position);
                goJobInfoActivity(item.getId());
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

    private void join(int id,int position) {
        dataProvider.work.join(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        mAdapter.getData().get(position).setIsJoin(true);
                        mAdapter.notifyItemChanged(position);
                        PopShowUtils.showTips(CollectedWorksActivity.this);
                    }
                });
    }
    /**
     * 已过期，点击删除
     * @param id
     * @param position
     */
    private void del(int id, int position) {
        dataProvider.work.fav(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        mAdapter.removeAt(position);
                    }
                });
    }
}