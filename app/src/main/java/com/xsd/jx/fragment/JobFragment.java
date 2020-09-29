package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.LoginActivity;
import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.InviteListResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.databinding.FragmentJobBinding;
import com.xsd.jx.job.JobPriceInquireActivity;
import com.xsd.jx.job.PermanentWorkerActivity;
import com.xsd.jx.job.SignActivity;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.BannerUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 找活
 */
public class JobFragment extends BaseBindFragment<FragmentJobBinding> {
    private static final String TAG = "JobFragment";
    private JobAdapter mAdapter = new JobAdapter();
    private int page;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {
        Apollo.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    @Override
    protected void onLazyLoad() {
        initView();
        loadData();
        getInviteList();
        onEvent();
    }


    //被邀请上工信息列表
    @Receive(EventStr.UPDATE_INVITE_LIST)
    public void getInviteList() {
        dataProvider.work.inviteList()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<InviteListResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<InviteListResponse> baseResponse) {
                        InviteListResponse response = baseResponse.getData();
                        List<JobBean> data = response.getItems();
                        int count = response.getCount();
                        if (data!=null&&data.size()>0){
                            db.radarView.setVisibility(View.VISIBLE);
                            db.tvInvite.setText(count+"个\n邀请");
                            db.tvInvite.setOnClickListener(v ->{
                                PopShowUtils.showInviteJob(data, (BaseActivity) JobFragment.this.getActivity());
                            } );
                        }
                    }
                });

    }


    private void initView() {
        AnimUtils.floatView(db.ivLq);
        //轮播图片
        List<Object> imgs = new ArrayList<>();
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        imgs.add(R.mipmap.banner_test);
        BannerUtils.initBanner(db.banner,imgs);
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db.recyclerView.setAdapter(mAdapter);
        //添加头部提示
        TextView tvNotice = (TextView) LayoutInflater.from(this.getActivity()).inflate(R.layout.tv_notice, null);
        mAdapter.addHeaderView(tvNotice);
    }
    //首页-推荐的工作列表
    private void loadData() {
        dataProvider.work.list(page,0)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkListResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<WorkListResponse> baseResponse) {
                        WorkListResponse data = baseResponse.getData();
                        List<JobBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });
    }




    private void onEvent() {
        int childCount = db.layoutTab.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            db.layoutTab.getChildAt(i).setOnClickListener(v -> {
                if (!UserUtils.isLogin()){
                    ToastUtil.showLong("请先登录！");
                    goActivity(LoginActivity.class);
                    return;
                }
                switch (finalI){
                    case 0://查工价
                        goActivity(JobPriceInquireActivity.class);
                        break;
                    case 1://突击工
                        goActivity(PermanentWorkerActivity.class,1);
                        break;
                    case 2://长期工
                        goActivity(PermanentWorkerActivity.class,2);
                        break;
                    case 3:
                        goActivity(SignActivity.class);
                        break;
                    case 4:
                        PopShowUtils.showShare((BaseActivity) this.getActivity());
                        break;
                }
            });
        }
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
                        join(item.getId(),position);
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

        //加载更多
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            page++;
            loadData();
        });

        //下拉刷新
        db.refreshLayout.setOnRefreshListener(() -> {
            page=1;
            loadData();
        });

    }

    private void join(int id,int position) {
        dataProvider.work.join(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        mAdapter.getData().get(position).setIsJoin(true);
                        mAdapter.notifyItemChanged(position+1);
                        PopShowUtils.showTips((BaseActivity) JobFragment.this.getActivity());
                    }
                });
    }



}
