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
import com.google.android.material.tabs.TabLayout;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lzf.easyfloat.EasyFloat;
import com.xsd.jx.R;
import com.xsd.jx.adapter.WorkerAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.pop.FilterPop;
import com.xsd.jx.pop.InviteJobsPop;
import com.xsd.jx.pop.SortPop;
import com.xsd.jx.databinding.ActivityGetWorkersBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.listener.OnWorkTypeSelectListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TextViewUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 招工人
 * 对应企业端首页
 * 头部包含：
 * 1.我的招工
 * 2.工人考勤
 * 3.工资结算
 * 4.发布招工
 */
public class GetWorkersActivity extends BaseBindBarActivity<ActivityGetWorkersBinding> {
    private WorkerAdapter mAdapter = new WorkerAdapter();
    private int page = 1;
    private int wtId;//工种ID
    private int sortBy = 1;//排序ID
    private int workId;//工作ID
    private List<WorkTypeBean> workTypes;//工种筛选数据

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        onEvent();
        loadData();
        EasyFloat.hideAppFloat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
        EasyFloat.showAppFloat();
    }

    @Receive(EventStr.UPDATE_GET_WORKERS)
    public void updateGetWorkers() {
        L.e("刷新招工人列表");
        page = 1;
        loadData();
    }

    private void loadData() {
        dataProvider.server.index(page, wtId, sortBy)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkerResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<WorkerResponse> baseResponse) {
                        WorkerResponse data = baseResponse.getData();
                        List<WorkerBean> items = data.getItems();
                        if (items != null && items.size() > 0) {
                            if (page == 1) mAdapter.setNewInstance(items);
                            else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        } else {
                            if (page == 1) mAdapter.setNewInstance(items);
                            else mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                        if (page == 1) {
                            setTabs(data.getWorkTypes());
                        }
                    }
                });
    }

    /**
     * 动态设置顶部工种选项Tabs
     * 1.首次进入工种ID:wtId=0,如果有tabs，就会默认选中第一项，触发选中事件后，会进行loadData()请求数据
     *
     */
    private void setTabs(List<WorkTypeBean> newWorkTypes) {
        //如果没有工种类型，就说明没有发布招工，隐藏tab项，显示去发布空布局，
        if (newWorkTypes.size() == 0) {
            db.layoutTabAll.setVisibility(View.GONE);
            AdapterUtils.setNoPushView(mAdapter,this);
            return;
        }
        //有工种，显示tab项，设置无数据空布局
        db.layoutTabAll.setVisibility(View.VISIBLE);
        AdapterUtils.setEmptyDataView(mAdapter);

        //1.没有tabs直接赋值，添加头部Tabs
        if (workTypes == null) {
            workTypes = newWorkTypes;
            updateTopTabs();
            return;
        }
        //2.如果已经有tabs了，而且条目和新的tabs条目数相同，就不改变
        if (workTypes.size() == newWorkTypes.size()) return;
        //3.如果有新增的tabs
        workTypes = newWorkTypes;
        updateTopTabs();
    }

    private void updateTopTabs() {
        db.tabLayout.removeAllTabs();
        List<String> tabNames = new ArrayList<>();
        for (WorkTypeBean workType : workTypes) {
            String title = workType.getTitle();
            tabNames.add(title);
            TabLayout.Tab tab = db.tabLayout.newTab();
            tab.setText(title);
            tab.setTag(workType.getId());
            db.tabLayout.addTab(tab);
        }
        if (workTypes.size() < 5) {
            db.tabLayout.setTabMode(TabLayout.MODE_FIXED);
            db.tvAll.setVisibility(View.GONE);
        } else {
            db.tvAll.setVisibility(View.VISIBLE);
            db.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            //只要更新了Tabs，同时就要更新全部
            filterPop = new FilterPop(GetWorkersActivity.this, workTypes);
            filterPop.setListener(new OnWorkTypeSelectListener() {
                @Override
                public void onSelect(WorkTypeBean workTypeBean) {
                    wtId = workTypeBean.getId();
                    //比较数据，确定滚动位置
                    int position = 0;
                    for (int i = 0; i < workTypes.size(); i++) {
                        if (wtId == workTypes.get(i).getId()) {
                            position = i;
                        }
                    }
                    db.tabLayout.getTabAt(position).select();
                    page = 1;
                    loadData();
                }
            });
            new XPopup.Builder(this)
                    .atView(db.tvAll)
                    .asCustom(filterPop);
        }

        //如果重新设置Tabs需要刷新列表除去重复数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData();
            }
        }, 800);
    }

    private void onEvent() {
        AppBarUtils.setColorGrayWhite(db.appbar, db.layoutCondition);
        db.setClicklistener(view -> {
            switch (view.getId()) {
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
                case R.id.tv_all:
                    db.appbar.setExpanded(false);
                    showFilterPop();
                    break;
            }
        });
        db.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page = 1;
                wtId = (int) tab.getTag();
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                WorkerBean item = (WorkerBean) adapter.getItem(position);
                Intent intent = new Intent(GetWorkersActivity.this, WorkerResumeActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("userId", item.getUserId());
                intent.putExtra("wtId", wtId);
                intent.putExtra("workId", workId);
                intent.putExtra("item", item);
                startActivity(intent);

            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_invite);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                WorkerBean item = (WorkerBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tv_invite://邀请上工
                        showInviteJobs(item, position);
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
                page = 1;
                loadData();
            }
        });
    }

    /**
     * 邀请上工
     * 查询当前发布者同工种有几条招工信息，
     * 如果有多条，弹出框选择上工地点
     */
    private void showInviteJobs(WorkerBean item, int position) {
        int userId = item.getUserId();
        dataProvider.server.invite(userId, wtId, workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<JobListResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<JobListResponse> baseResponse) {
                        JobListResponse data = baseResponse.getData();
                        List<JobListResponse.ItemsBean> items = data.getItems();
                        if (items != null && items.size() > 0) {
                            //如果有多条，弹出框选择上工地点
                            InviteJobsPop inviteJobsPop = new InviteJobsPop(GetWorkersActivity.this, items);
                            inviteJobsPop.setListener(itemsBean -> {
                                workId = itemsBean.getWorkId();
                                showInviteJobs(item, position);
                            });
                            new XPopup.Builder(GetWorkersActivity.this)
                                    .asCustom(inviteJobsPop)
                                    .show();

                        } else {
                            try {
                                ToastUtil.showLong(baseResponse.getData().getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            item.setInvited(true);
                            mAdapter.notifyItemChanged(position);

                        }
                    }
                });
    }

    private SortPop sortPop;

    private void showSortPop() {
        if (sortPop == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sortPop = new SortPop(GetWorkersActivity.this);
                    new XPopup.Builder(GetWorkersActivity.this)
                            .atView(db.tvSort)
                            .setPopupCallback(new SimpleCallback() {
                                @Override
                                public void onShow(BasePopupView popupView) {
                                    super.onShow(popupView);
                                    db.tvSort.setTextColor(ContextCompat.getColor(GetWorkersActivity.this, R.color.tv_blue));
                                    TextViewUtils.setLeftIcon(R.mipmap.ic_blue_arrow_up_down, db.tvSort);
                                }

                                @Override
                                public void onDismiss(BasePopupView popupView) {
                                    super.onDismiss(popupView);
                                    db.tvSort.setTextColor(ContextCompat.getColor(GetWorkersActivity.this, R.color.tv_darkgray));
                                    TextViewUtils.setLeftIcon(R.mipmap.ic_gray_arrow_up_down, db.tvSort);
                                }
                            })
                            .asCustom(sortPop)
                            .show();
                }
            }, 200);
        } else {
            sortPop.show();
        }
    }

    //点击全部，显示所有的工种
    private FilterPop filterPop;
    private void showFilterPop() {
        if (filterPop == null) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                filterPop.show();
            }
        }, 300);

    }

    private void initView() {
        tvTitle.setText("招工人");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        AnimUtils.potView(db.ivPot);
    }
}