package com.xsd.jx.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.GetWorkersInfoAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.PaidResponse;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.custom.PayTypePop;
import com.xsd.jx.custom.WaitPayBillPop;
import com.xsd.jx.databinding.ActivityGetWorkersInfoBinding;
import com.xsd.jx.listener.OnPayListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.TabUtils;
import com.xsd.utils.ActivityCollector;
import com.xsd.utils.ClipboardUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 招工详情:8个状态
 status 状态 -1:不展示(有预付款项未付不显示给用户) )
 1:正在招
 2:已招满/待开工(所有用户已确认)
 3:工期中
 4:待结算
 5:待评价
 6:已完成
 7:已取消
 8:已过期
 */
public class GetWorkersInfoActivity extends BaseBindBarActivity<ActivityGetWorkersInfoBinding> {
    private GetWorkersInfoAdapter mAdapter;
    private int workId;//当前详情的工种ID
    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        onEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    @Receive(EventStr.CLOSE_GET_WORKERSINFO_ACTIVITY)
    public void closePage(){
        finish();
    }

    @Receive(EventStr.UPDATE_COMMENT_LIST)
    public void update(){
        L.e("关闭===");
        finish();
    }

    private void onEvent() {
        //layoutHead为appbar中的一个控件
       AppBarUtils.setColorGrayWhite(db.appbar,db.tabLayout);
       db.setClicklistener(view -> {
           switch (view.getId()){
               case R.id.layout_need_pay://显示结算明细
                   showWaitPayBill();
                   break;
               case R.id.tv_together_comment://统一评价工人
                   Intent intent = new Intent(GetWorkersInfoActivity.this, TogetherCommentActivity.class);
                   intent.putExtra("workId",item.getId());
                   intent.putExtra("workers", (Serializable) item.getWorkers());
                   startActivity(intent);
                   break;
               case R.id.tv_copy://复制订单编号
                   String s = db.tvSn.getText().toString();
                   ClipboardUtils.copy(s);
                   break;
               case R.id.tv_cancel://取消招聘
                   cancelInvite();
                   break;
               case R.id.tv_activ_get://主动招人
                   ActivityCollector.finishActivity(MyGetWorkersActivity.class);
                   finish();
                   break;
               case R.id.tv_pay://结算
                   showPayType();
                   break;
           }
       });
       mAdapter.addChildClickViewIds(R.id.tv_cancel,R.id.tv_confirm,R.id.tv_look,R.id.tv_name,R.id.tv_single_comment,R.id.tv_activ_get);
       mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
           @Override
           public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
               WorkerBean item = (WorkerBean) adapter.getItem(position);
               int userId = item.getUserId();
               switch (view.getId()){
                   case R.id.tv_look://查看工人简历
                       Intent intent = new Intent(GetWorkersInfoActivity.this, WorkerResumeActivity.class);
                       intent.putExtra("type",1);
                       intent.putExtra("userId",item.getUserId());
                       intent.putExtra("workId",workId);
                       intent.putExtra("status",item.getStatus());
                       startActivity(intent);
                       break;
                   case R.id.tv_cancel://婉拒
                       doJoinWork(userId,1,position);
                       break;
                   case R.id.tv_confirm://雇佣
                       doJoinWork(userId,2,position);
                       break;
                   case R.id.tv_name://打电话
                       MobileUtils.callPhone(GetWorkersInfoActivity.this,item.getMobile());
                       break;
                   case R.id.tv_single_comment://单独评价，用工方评价上工者
                       Intent intent1 = new Intent(GetWorkersInfoActivity.this, TogetherCommentActivity.class);
                       intent1.putExtra("workId",workId);
                       intent1.putExtra("workers", (Serializable) Arrays.asList(item));
                       startActivity(intent1);
                       break;
                   case R.id.tv_activ_get://主动招人
                       ActivityCollector.finishActivity(MyGetWorkersActivity.class);
                       finish();
                       break;
               }
           }
       });

    }

    private void cancelInvite() {
        PopShowUtils.showConfirm(this,
                "您是否确定取消招工？取消后若有已支付款项将退回至您的平台账户余额。",
                "我再想想",
                "确认取消", () -> dataProvider.server.cancelWork(workId)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                            @Override
                            protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                ToastUtil.showLong(baseResponse.getData().getMessage());
                                finish();
                                Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                            }
                        }));

    }

    /**
     * 拒绝/雇佣报名用户
     * @param userId 报名用户ID
     * @param type 类型 1:拒绝 2:雇佣
     */
    private void doJoinWork(int userId,int type,int position){
        dataProvider.server.doJoinWorker(workId,userId,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mAdapter.removeAt(position);
                        if (mAdapter.getData().size()==0){
                            finish();
                        }
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                        if (type==1){//拒绝:待确认工人数-1
                            int tobeConfirmNum = item.getTobeConfirmNum();
                            tobeConfirmNum--;
                            item.setTobeConfirmNum(tobeConfirmNum);
                            //拒绝后刷新我的招工列表
                        }else {//雇佣:待确认工人数-1，确认上工人数+1
                            int tobeConfirmNum = item.getTobeConfirmNum();
                            int confirmedNum = item.getConfirmedNum();
                            tobeConfirmNum--;
                            confirmedNum++;
                            item.setTobeConfirmNum(tobeConfirmNum);
                            item.setConfirmedNum(confirmedNum);
                        }
                        db.setItem(item);
                        Apollo.emit(EventStr.UPDATE_MY_GET_WORKERS);

                    }
                });
    }

    private void showWaitPayBill() {
        new XPopup.Builder(this)
                .atView(db.layoutNeedPay)
                .isRequestFocus(false)
                .asCustom(new WaitPayBillPop(this,item))
                .show();
    }

    private int type;
    private MyGetWorkersResponse.ItemsBean item;
    private void initView() {
        item = (MyGetWorkersResponse.ItemsBean) getIntent().getSerializableExtra("item");
        String price = item.getPrice();//工价
        db.setItem(item);
        workId = item.getId();
        type = item.getItemType();

        mAdapter = new GetWorkersInfoAdapter(price);
        switch (type){
            case 1:
                db.tvTitle.setText("正在招");
                break;
            case 2:
                db.tvTitle.setText("已招满");
                db.layoutBtns.setVisibility(View.GONE);
                break;
            case 3:
                db.tvTitle.setText("工期内");//也可以结算
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.layoutNeedPay.setVisibility(View.VISIBLE);
                db.tvPay.setVisibility(View.VISIBLE);
                break;
            case 4:
                db.tvTitle.setText("待结算");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.layoutNeedPay.setVisibility(View.VISIBLE);
                db.tvPay.setVisibility(View.VISIBLE);
                //还需支付

                break;
            case 5:
                db.tvTitle.setText("待评价");
                db.tvCancel.setVisibility(View.GONE);
                db.tvActivGet.setVisibility(View.GONE);
                db.tvTogetherComment.setVisibility(View.VISIBLE);
                break;
            case 6:
                db.tvTitle.setText("已完成");
                db.layoutPayedTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("已结清（3200元）");
                setTopColor();
                break;
            case 7:
                db.tvTitle.setText("已取消");
                db.layoutCancelTime.setVisibility(View.VISIBLE);
                db.tvPayedMoney.setText("2成/1200元(已退至您的账户钱包)");
                setTopColor();
                break;
        }
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList(type==1?"报名工人列表":"工人列表","招工详情"), new OnTabClickListener() {
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
        emptyView.findViewById(R.id.tv_get_workers).setOnClickListener(view -> {
            ActivityCollector.finishActivity(MyGetWorkersActivity.class);
            finish();
        });
        mAdapter.setEmptyView(emptyView);
        //工人列表
        List<WorkerBean> workers = item.getWorkers();
        //只有正在招状态下的工人，需要屏蔽掉拒绝和确认的工人
        if (type==1){
            List<WorkerBean> showWorkers = new ArrayList<>();
            for (int i = 0; i < workers.size(); i++){
                WorkerBean workerBean = workers.get(i);
                int status = workerBean.getStatus();//状态 1:未处理 2：已确认 3：已拒绝:拒绝和确认都不再显示
                if (status==1){
                    workerBean.setType(type);
                    showWorkers.add(workerBean);
                }
            }
            mAdapter.setList(showWorkers);
        }else {
            for (int i = 0; i < workers.size(); i++) workers.get(i).setType(type);
            mAdapter.setList(workers);
        }


    }


    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
    }

    //支付方式
    private int payment=1;//支付方式:1:微信 2:支付宝
    PayTypePop payTypePop;
    private void showPayType() {
        if (payTypePop==null){
            payTypePop = new PayTypePop(this);
            payTypePop.setListener(new OnPayListener() {
                @Override
                public void wxPay() {
                    payment=1;
                    submitPay();
                }

                @Override
                public void aliPay() {
                    payment=2;
                    submitPay();
                }
            });
            new XPopup.Builder(this)
                    .asCustom(payTypePop)
                    .show();
        }else {
            payTypePop.show();
        }
    }
    private void submitPay() {
        dataProvider.server.doSettle(payment,item.getId()+"")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PaidResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PaidResponse> baseResponse) {
                        ToastUtil.showLong("支付成功！订单号："+baseResponse.getData().getOrderId());
                        finish();
                        Apollo.emit(EventStr.UPDATE_MY_GET_WORKERS);
                    }
                });
    }
}