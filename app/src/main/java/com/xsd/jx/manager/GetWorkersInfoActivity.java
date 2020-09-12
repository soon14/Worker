package com.xsd.jx.manager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersInfoAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.WorkerResponse;
import com.xsd.jx.custom.WaitPayBillPop;
import com.xsd.jx.databinding.ActivityGetWorkersInfoBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.TabUtils;

import java.util.Arrays;

/**
 * 招工详情
 */
public class GetWorkersInfoActivity extends BaseBindBarActivity<ActivityGetWorkersInfoBinding> {
    private GetWorkersInfoAdapter mAdapter = new GetWorkersInfoAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }


    private void onEvent() {
        //layoutHead为appbar中的一个控件
       AppBarUtils.setColorGrayWhite(db.appbar,db.tabLayout);
       db.setClicklistener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               switch (view.getId()){
                   case R.id.layout_need_pay:
                       showWaitPayBill();
                       break;
                   case R.id.tv_together_comment:
                       goActivity(TogetherCommentActivity.class);
                       break;
               }
           }
       });

    }

    private void showWaitPayBill() {
        new XPopup.Builder(this)
                .atView(db.layoutNeedPay)
                .asCustom(new WaitPayBillPop(this))
                .show();
    }

    int type;
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        switch (type){
            case 0:
                db.tvTitle.setText("正在招");
                break;
            case 1:
                db.tvTitle.setText("已招满");
                db.layoutBtns.setVisibility(View.GONE);
                break;
            case 2:
                db.tvTitle.setText("工期内");
                break;
            case 3:
                db.tvTitle.setText("待结算");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.layoutNeedPay.setVisibility(View.VISIBLE);
                db.tvPay.setVisibility(View.VISIBLE);
                break;
            case 4:
                db.tvTitle.setText("待评价");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.tvTogetherComment.setVisibility(View.VISIBLE);
                break;
            case 5:
                db.tvTitle.setText("已完成");
                db.layoutPayedTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("已结清（3200元）");
                setTopColor();
                break;
            case 6:
                db.tvTitle.setText("已取消");
                db.layoutCancelTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("2成/1200元(已退至您的账户钱包)");
                setTopColor();
                break;
        }
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList(type==0?"报名工人列表":"工人列表","招工详情"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0:
                        db.recyclerView.setVisibility(View.VISIBLE);
                        db.layoutInfo.setVisibility(View.GONE);
                        break;
                    case 1:
                        db.recyclerView.setVisibility(View.GONE);
                        db.layoutInfo.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_noperson, null);
        emptyView.findViewById(R.id.tv_get_workers).setOnClickListener(view -> goActivity(PushGetWorkersActivity.class));
        mAdapter.setEmptyView(emptyView);

        for (int i = 0; i < 8; i++) {
            mAdapter.addData(new WorkerResponse(type));
        }

        //查看工人详情，需要显示婉拒和雇佣
        mAdapter.addChildClickViewIds(R.id.tv_look);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_look:
                        goActivity(WorkerResumeActivity.class,1);
                        break;
                }
            }
        });
    }


    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
    }
}