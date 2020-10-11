package com.xsd.jx.manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.BottomListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.WorkerSignListAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.databinding.ActivityWorkerSignListBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 企业端：
 *
 * 考勤记录
 * 如果是今天之前的日子没打卡，那么此处显示“
 * 您当天处于工期内但未考勤，这将影响您的工资结算，若忘记考勤可联系雇主修改
 * ”
 */
public class WorkerSignListActivity extends BaseBindBarActivity<ActivityWorkerSignListBinding> {
    private WorkerSignListAdapter mAdapter = new WorkerSignListAdapter();
    private String date="";
    private int workId;//工作ID 默认：最近的1个招工ID,赋值为workingList第一个值
    private int status;//状态 0:全部 1：未考勤 2:已考勤

    List<WorkCheckLogResponse.ItemsBean> items;
    List<WorkCheckLogResponse.ItemsBean> items1;
    List<WorkCheckLogResponse.ItemsBean> items2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void initView() {
        date = getIntent().getStringExtra("date");
        tvTitle.setText("考勤记录");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
    }

    /**
     * 考勤记录
     * 根据日期获取某个招工的所有用户的考勤信息
     */
    private void loadData() {
        dataProvider.server.workCheckLog(date,workId,status)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkCheckLogResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkCheckLogResponse> baseResponse) {
                        WorkCheckLogResponse data = baseResponse.getData();
                        List<WorkCheckLogResponse.WorkingItem> workingList = data.getWorkingList();
                        initTopWorkingList(workingList);
                        initData(data);
                    }
                });
    }
    BottomListPopupView bottomWorkingList;
    private void initTopWorkingList(List<WorkCheckLogResponse.WorkingItem> workingList) {
        if (workingList==null||workingList.size()==0)return;
        //默认设置第一项
        WorkCheckLogResponse.WorkingItem workingItem0 = workingList.get(0);
        workId = workingItem0.getId();
        db.tvTypeTitle.setText(workingItem0.getTypeTitle());
        db.tvTime.setText( workingItem0.getStartDate() + "至" +  workingItem0.getEndDate());


        String[] strings = new String[workingList.size()];

        for (int i = 0; i < workingList.size(); i++) {
            WorkCheckLogResponse.WorkingItem workingItem = workingList.get(i);
            String typeTitle = workingItem.getTypeTitle();
            String startDate = workingItem.getStartDate();
            String endDate = workingItem.getEndDate();
            strings[i] = typeTitle +"("+startDate+"至"+endDate+")";

        }

         bottomWorkingList = new XPopup.Builder(this)
                .asBottomList("工期选择",
                        strings,
                        null,
                        -1,
                        false,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                WorkCheckLogResponse.WorkingItem workingItem = workingList.get(position);
                                String typeTitle = workingItem.getTypeTitle();
                                String startDate = workingItem.getStartDate();
                                String endDate = workingItem.getEndDate();
                                db.tvTypeTitle.setText(typeTitle);
                                db.tvTime.setText(startDate + "至" + endDate);
                            }
                        },
                        0,
                        0);
    }

    /**
     * 把所有考勤分成
     * 全部记录
     * 未考勤 没有上工时间
     * 已考勤 2
     * @param items 考勤工人
     */
    private void initData(WorkCheckLogResponse data) {
        String totalNum = data.getTotalNum();
        String notCheckNum = data.getNotCheckNum();
        String checkNum = data.getCheckNum();
        items = data.getItems();
        items1 = new ArrayList<>();
        items2 = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            WorkCheckLogResponse.ItemsBean itemsBean = items.get(i);
            String signInTime = itemsBean.getSignInTime();
            if (TextUtils.isEmpty(signInTime))items1.add(itemsBean);
            if (!TextUtils.isEmpty(signInTime))items2.add(itemsBean);
        }
        mAdapter.setList(items);
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList("全部记录("+totalNum+")","未考勤("+notCheckNum+")","已考勤("+checkNum+")"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0:
                        mAdapter.setList(items);
                        break;
                    case 1:
                        mAdapter.setList(items1);
                        break;
                    case 2:
                        mAdapter.setList(items2);
                        break;
                }
            }
        });

    }

    private void onEvent() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                WorkCheckLogResponse.ItemsBean item = (WorkCheckLogResponse.ItemsBean) adapter.getItem(position);
                Intent intent = new Intent(WorkerSignListActivity.this, WorkerSignInfoActivity.class);
                intent.putExtra("workId",workId);
                intent.putExtra("yearMonth",date.substring(0,date.lastIndexOf("-")));
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_name,R.id.tv_confirm_sign);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WorkCheckLogResponse.ItemsBean item = (WorkCheckLogResponse.ItemsBean) adapter.getItem(position);
            switch (view.getId()){
                case R.id.tv_name://点击名字和电话图标：打电话
                    MobileUtils.callPhone(WorkerSignListActivity.this,item.getMobile());
                    break;
                case R.id.tv_confirm_sign://确认考勤
                    confirmCheckLog(item.getCheckId(),position);
                    break;
            }
        });
        db.layoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomWorkingList!=null&&!bottomWorkingList.isShow())bottomWorkingList.show();
            }
        });
    }

    /**
     * 确认考勤
     * 获取日期获取某个招工的所有用户的考勤信息
     */
    private void confirmCheckLog(int checkId,int position) {
        dataProvider.server.confirmCheckLog(checkId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mAdapter.getItem(position).setStatus(1);
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }


}