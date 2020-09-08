package com.xsd.jx.manager;

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
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.custom.FilterPop;
import com.xsd.jx.custom.InviteJobsPop;
import com.xsd.jx.custom.SortPop;
import com.xsd.jx.databinding.ActivityGetWorkersBinding;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.TextViewUtils;

/**
 * 招工人
 */
public class GetWorkersActivity extends BaseBindBarActivity<ActivityGetWorkersBinding> {
    private WorkerAdapter mAdapter = new WorkerAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
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
                goActivity(WorkerResumeActivity.class);
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
        for (int i = 0; i < 20; i++) {
            mAdapter.addData(new WorkerResponse(0));
        }
    }
}