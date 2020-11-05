package com.xsd.jx.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alipay.sdk.app.PayTask;
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
import com.xsd.jx.bean.PayResult;
import com.xsd.jx.bean.UnmatchedResponse;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.pop.PayTypePop;
import com.xsd.jx.pop.WaitPayBillPop;
import com.xsd.jx.databinding.ActivityGetWorkersInfoBinding;
import com.xsd.jx.listener.OnPayListener;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.AppBarUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.TabUtils;
import com.xsd.utils.ActivityCollector;
import com.xsd.utils.ClipboardUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.TimeUtils;
import com.xsd.utils.ToastUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 招工详情:8个状态
 状态 -1:不展示(有预付款项未付不显示给用户) )
 itemType
 1:正在招
 报名工人数 ，所需工人数 ,已雇佣人数
 2:已招满/待开工(所有用户已确认)
 3:工期中
 4:待结算
 5:待评价
 6:已完成
 7:已取消
 8:已过期

 - 顶部描述：
 工期中，待结算状态为：确认上工人数confirmedNum ，所需工人数num ,工价/天price
 其他状态：报名工人数tobeConfirmNum，所需工人数num，已雇佣人数confirmedNum
 状态为待开工，且startDate <=今天，显示确认开工按钮

 招工人{@link GetWorkersActivity} >> 我的招工{@link MyGetWorkersActivity} >> 【招工详情】

 */
public class GetWorkersInfoActivity extends BaseBindBarActivity<ActivityGetWorkersInfoBinding> {
    private GetWorkersInfoAdapter mAdapter;
    private int workId;//当前详情的工种ID
    private int itemType;//8个状态
    private MyGetWorkersResponse.ItemsBean item;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_workers_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apollo.bind(this);
        initView();
        loadData();
    }
    private void initView() {
        workId = getIntent().getIntExtra("type", 1);
    }
    private void loadData() {
        dataProvider.server.workDetail(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MyGetWorkersResponse.ItemsBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MyGetWorkersResponse.ItemsBean> baseResponse) {
                        item = baseResponse.getData();
                        itemType = item.getItemType();//就是status
                        initData();
                        //状态为待开工，且startDate <=今天，显示确认开工按钮
                        Date startDate = TimeUtils.strToDate(item.getStartDate());
                        Date currentDate = TimeUtils.strToDate(TimeUtils.getTodayDate());
                        if (itemType==2&&startDate.getTime()<=currentDate.getTime()){
                            db.tvConfirmStart.setVisibility(View.VISIBLE);
                        }
                    }
                });
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
        finish();
    }

    private void initData() {
        String price = item.getPrice();//工价
        db.setItem(item);
        mAdapter = new GetWorkersInfoAdapter(price);
        AdapterUtils.setEmptyDataView(mAdapter);
        switch (itemType){
            case 1:
                db.tvTitle.setText("正在招");
                View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view_noperson, null);
                emptyView.findViewById(R.id.tv_get_workers).setOnClickListener(view -> {
                    ActivityCollector.finishActivity(MyGetWorkersActivity.class);
                    finish();
                });
                mAdapter.setEmptyView(emptyView);
                break;
            case 2:
                db.tvTitle.setText("待开工");
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
                setTopColor();
                break;
            case 7:
                db.tvTitle.setText("已取消");
                db.layoutCancelTime.setVisibility(View.VISIBLE);
                setTopColor();
                break;
            case 8:
                db.tvTitle.setText("已过期");
                db.layoutCancelTime.setVisibility(View.VISIBLE);
                setTopColor();
                break;
        }
        TabUtils.setDefaultTab(this, db.tabLayout, Arrays.asList(itemType ==1?"报名工人列表":"工人列表","招工详情"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
//                if (itemType==6||itemType==7||itemType==8)return;
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



        //工人列表
        List<WorkerBean> workers = item.getWorkers();
        //只要我的订单有1个已确认的工人，那么就隐藏取消招聘按钮
        boolean hasConfirmWorker = false;//是否有已经确认的工人
        for (int i = 0; i < workers.size(); i++) {
            WorkerBean workerBean = workers.get(i);
            int status = workerBean.getStatus();
            workerBean.setType(itemType);
            if (status==2)hasConfirmWorker=true;
        }
        mAdapter.setList(workers);
        if (hasConfirmWorker)db.tvCancel.setVisibility(View.GONE);
        onEvent();
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
//                   showPayType();
                   submitPay();
                   break;
               case R.id.tv_confirm_start://确认开工
                   confirmStart();
                   break;
           }
       });
       //企业端-招工人详情页，工人列表子项点击事件
       mAdapter.addChildClickViewIds(R.id.tv_cancel,R.id.tv_confirm,R.id.tv_look,R.id.tv_name,R.id.tv_single_comment,R.id.tv_activ_get,R.id.layout_look_persion_day);
       mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
           WorkerBean item = (WorkerBean) adapter.getItem(position);
           int userId = item.getUserId();
           switch (view.getId()){
               case R.id.tv_look://查看工人简历
                   Intent intent = new Intent(GetWorkersInfoActivity.this, WorkerResumeActivity.class);
                   intent.putExtra("type",1);
                   intent.putExtra("workId",workId);
                   intent.putExtra("item",item);
                   startActivity(intent);
                   break;
               case R.id.tv_cancel://婉拒
                   doJoinWork(item.getJoinId(),1,position,false);
                   break;
               case R.id.tv_confirm://雇佣
                   doJoinWork(item.getJoinId(),2,position,false);
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
               case R.id.layout_look_persion_day://查看工时
                   PopShowUtils.showDayPersion(this,item);
                   break;
           }
       });

    }

    private void confirmStart() {
        dataProvider.server.confirmWork(workId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.UPDATE_MY_GET_WORKERS);
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
     * @param joinId 报名ID
     * @param type 类型 1:拒绝 2:雇佣
     */
    private void doJoinWork(int joinId,int type,int position,boolean isConfirmed){
        dataProvider.server.doJoinWorker(joinId,type,isConfirmed)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UnmatchedResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UnmatchedResponse> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mAdapter.removeAt(position);
                        if (mAdapter.getData().size()==0){
//                            finish();
                        }

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
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                        Apollo.emit(EventStr.UPDATE_MY_GET_WORKERS);
                        loadData();

                    }

                    @Override
                    protected void onErr(BaseResponse baseResponse) {
                        super.onErr(baseResponse);
                        UnmatchedResponse unmatchedResponse = (UnmatchedResponse) baseResponse.getData();
                        L.e("unmatchedResponse=="+unmatchedResponse);
//                        UnmatchedResponse unmatchedResponse = GsonUtils.jsonToObj(dataStr, UnmatchedResponse.class);
                        PopShowUtils.showConfirmEmployNum(GetWorkersInfoActivity.this, unmatchedResponse, () -> doJoinWork(joinId,type,position,true));
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

    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
    }

    //支付方式
    private int payment=2;//支付方式:1:微信 2:支付宝
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
        int tobeSettledWage = item.getTobeSettledWage();//共待结算工资
        dataProvider.server.doSettle(payment, item.getId()+"")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PaidResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PaidResponse> baseResponse) {
                        PaidResponse data = baseResponse.getData();

                        if (tobeSettledWage>0){
                            String orderString = data.getOrderString();
                            aliPay(orderString);
                        }else {
                            ToastUtil.showLong("结算成功！");
                            loadData();
                        }


                    }
                });
    }
    private static final int SDK_ALIPAY_FLAG = 1;
    private static final int SDK_WXPAY_FLAG = 2;
    private void aliPay(String orderInfo){
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(GetWorkersInfoActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            L.e("msp", result.toString());
            Message msg = new Message();
            msg.what = SDK_ALIPAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case SDK_ALIPAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultStatus = payResult.getResultStatus();
                if (TextUtils.equals(resultStatus, "9000")) {
                    PopShowUtils.showLoad(GetWorkersInfoActivity.this, "结算成功，结算统计中...", popupView -> {
                        finish();
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                        Apollo.emit(EventStr.UPDATE_MY_GET_WORKERS);
                    });
                } else {
                    ToastUtil.showLong("支付失败");
                }
                break;
            }
            case SDK_WXPAY_FLAG:
                break;
            default:
                break;
        }
        return false;
    });
}