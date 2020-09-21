package com.xsd.jx.manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.xsd.jx.R;
import com.xsd.jx.adapter.WorkerAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.custom.FilterPop;
import com.xsd.jx.custom.InviteJobsPop;
import com.xsd.jx.custom.SortPop;
import com.xsd.jx.databinding.ActivityGetWorkersBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TextViewUtils;

import java.util.List;

/**
 * 招工人
 * 对应企业端首页
 */
public class GetWorkersActivity extends BaseBindBarActivity<ActivityGetWorkersBinding> {
    private WorkerAdapter mAdapter = new WorkerAdapter();
    private int page=1;
    private int wtId=1;
    private int sortBy=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void loadData() {
        dataProvider.server.index(page,wtId,sortBy)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkerResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkerResponse> baseResponse) {
                        WorkerResponse data = baseResponse.getData();
                        List<WorkerBean> items = data.getItems();
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

    private void onEvent() {
        AppBarUtils.setColorGrayWhite(db.appbar,db.layoutCondition);
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.tv_my_getworkers:
                    goActivity(MyGetWorkersActivity.class);
                    break;
                case R.id.tv_worker_sign:
                    goActivity(WorkerSignActivity.class);
                    break;
                case R.id.tv_payed:
                    goActivity(WagePayActivity.class);
                    break;
                case R.id.tv_filter:
                    db.appbar.setExpanded(false);
                    showFilterPop();
                    break;
                case R.id.tv_sort:
                    db.appbar.setExpanded(false);
                    showSortPop();
                    break;
                case R.id.tv_push:
                case R.id.tv_push_pop:
                    goActivity(PushGetWorkersActivity.class);
                    break;
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                WorkerBean item = (WorkerBean) adapter.getItem(position);
                Intent intent = new Intent(GetWorkersActivity.this, WorkerResumeActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("userId",item.getUserId());
                startActivity(intent);

            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_invite);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_invite:
                        showInviteJobs();
                        break;
                }
            }
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

    private void showInviteJobs() {
        new XPopup.Builder(this)
                .asCustom(new InviteJobsPop(this))
                .show();
    }

    private SortPop sortPop;
    private void showSortPop() {
        if (sortPop==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            sortPop = new SortPop(GetWorkersActivity.this);
            new XPopup.Builder(GetWorkersActivity.this)
                    .atView(db.tvSort)
                    .setPopupCallback(new SimpleCallback(){
                        @Override
                        public void onShow(BasePopupView popupView) {
                            super.onShow(popupView);
                            db.tvSort.setTextColor(ContextCompat.getColor(GetWorkersActivity.this,R.color.tv_blue));
                            TextViewUtils.setLeftIcon(R.mipmap.ic_blue_arrow_up_down,db.tvSort);
                        }

                        @Override
                        public void onDismiss(BasePopupView popupView) {
                            super.onDismiss(popupView);
                            db.tvSort.setTextColor(ContextCompat.getColor(GetWorkersActivity.this,R.color.tv_darkgray));
                            TextViewUtils.setLeftIcon(R.mipmap.ic_gray_arrow_up_down,db.tvSort);
                        }
                    })
                    .asCustom(sortPop)
                    .show();
                }
            },200);
        }else {
            sortPop.show();
        }
    }

    private FilterPop filterPop;
    private void showFilterPop() {
        if (filterPop==null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    filterPop = new FilterPop(GetWorkersActivity.this);
                    new XPopup.Builder(GetWorkersActivity.this)
                            .atView(db.tvFilter)
                            .setPopupCallback(new SimpleCallback(){
                                @Override
                                public void onShow(BasePopupView popupView) {
                                    super.onShow(popupView);
                                    db.tvFilter.setTextColor(ContextCompat.getColor(GetWorkersActivity.this,R.color.tv_blue));
                                    TextViewUtils.setLeftIcon(R.mipmap.ic_blue_filter,db.tvFilter);
                                }

                                @Override
                                public void onDismiss(BasePopupView popupView) {
                                    super.onDismiss(popupView);
                                    db.tvFilter.setTextColor(ContextCompat.getColor(GetWorkersActivity.this,R.color.tv_darkgray));
                                    TextViewUtils.setLeftIcon(R.mipmap.ic_gray_filter,db.tvFilter);
                                }
                            })
                            .asCustom(filterPop)
                            .show();
                }
            },200);
        }else {
            filterPop.show();
        }
    }

    private void initView() {
        tvTitle.setText("招工人");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
    }
}