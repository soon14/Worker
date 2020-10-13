package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.LoginActivity;
import com.xsd.jx.R;
import com.xsd.jx.adapter.JobAdapter;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BannerBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkListResponse;
import com.xsd.jx.databinding.FragmentJobBinding;
import com.xsd.jx.job.JobPriceInquireActivity;
import com.xsd.jx.job.PermanentWorkerActivity;
import com.xsd.jx.job.SignActivity;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.BannerUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 找活
 */
public class JobFragment extends BaseBindFragment<FragmentJobBinding> {
    private static final String TAG = "JobFragment";
    private JobAdapter mAdapter = new JobAdapter();
    private int page=1;
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
        onEvent();
    }


    @Receive(EventStr.LOGIN_OUT)
    public void loginOut(){
        mAdapter.setList(null);
    }
    @Receive(EventStr.LOGIN_SUCCESS)
    public void loginSuccess(){
        page=1;
        loadData();
    }




    private void initView() {
        AnimUtils.floatView(db.ivLq);
        //轮播图片
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db.recyclerView.setAdapter(mAdapter);
        //1142*380
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
                        if (page==1){
                            List<BannerBean> banners = data.getBanners();
                            BannerUtils.initBanner(db.banner,banners);
                        }
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
                    case 3://考勤打卡
                        if (!UserUtils.isCertification()){
                            PopShowUtils.showRealNameAuth((BaseActivity) this.getActivity());
                            return;
                        }
                        goActivity(SignActivity.class);
                        break;
                    case 4:
                        PopShowUtils.showShare((BaseActivity) this.getActivity());
                        break;
                }
            });
        }
        mAdapter.addChildClickViewIds(R.id.tv_join);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
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
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!UserUtils.isLogin()){
                ToastUtil.showLong("请先登录！");
                goActivity(LoginActivity.class);
                return;
            }
            JobBean item = (JobBean) adapter.getItem(position);
            goJobInfoActivity(item.getId());
        });

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

    private void join(int id,int position) {
        dataProvider.work.join(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        mAdapter.getData().get(position).setIsJoin(true);
                        mAdapter.notifyItemChanged(position+1);
                        PopShowUtils.showTips((BaseActivity) JobFragment.this.getActivity());
                        //报名成功，刷新订单
                        Apollo.emit(EventStr.UPDATE_ORDER_LIST);
                    }

                    @Override
                    protected void onErr(String err) {
                        PopShowUtils.showMsg((BaseActivity) JobFragment.this.getActivity(),err);
                    }
                });
    }



}
