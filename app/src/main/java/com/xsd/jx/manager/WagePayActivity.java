package com.xsd.jx.manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.alipay.sdk.app.PayTask;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.PaidResponse;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.custom.PayTypePop;
import com.xsd.jx.databinding.ActivityWagePayBinding;
import com.xsd.jx.databinding.ItemWagePayBinding;
import com.xsd.jx.databinding.ItemWagepayWorkerBinding;
import com.xsd.jx.listener.OnPayListener;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TextViewUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 工资结算
 */
public class WagePayActivity extends BaseBindBarActivity<ActivityWagePayBinding> {
    private String ids;//选择提交的结算工作ID，用英文逗号分隔 1,2,3,4
    private int payment=1;//支付方式:1:微信 2:支付宝
    private List<ToSettleResponse.ItemsBean> items;
    private int totalNeedPayAmount;//总共还需支付的金额
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wage_pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void loadData() {
        dataProvider.server.settle()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<ToSettleResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<ToSettleResponse> baseResponse) {
                        ToSettleResponse data = baseResponse.getData();
                        items = data.getItems();
                        bindData();
                    }
                });

    }

    private void onEvent() {
        db.tvPayedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSettle();
            }
        });
        //底部全选按钮事件
        db.cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    int childCount = db.layoutContent.getChildCount();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < childCount; i++) {
                        View viewItem = db.layoutContent.getChildAt(i);
                        ItemWagePayBinding bind = DataBindingUtil.bind(viewItem);
                        bind.cbSelect.setChecked(true);
                        ToSettleResponse.ItemsBean itemsBean = items.get(i);
                        //根据是否有工人来判断是否需要结算此笔订单，从而确定是否要添加结算id和统计结算工资
                        List<ToSettleResponse.ItemsBean.UsersBean> users = itemsBean.getUsers();
                        if (users!=null&&users.size()>0){
                            builder.append(itemsBean.getId()).append(",");
                            int needPayAmount = itemsBean.getNeedPayAmount();//每单还需支付
                            totalNeedPayAmount += needPayAmount;
                        }
                    }
                    //用于结算的id集合
                    String idStr = builder.toString();
                    ids= idStr.substring(0,idStr.length()-1);
                    db.cbSelectAll.setText("全选合计："+totalNeedPayAmount+"元");

                }else {
                    int childCount = db.layoutContent.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View viewItem = db.layoutContent.getChildAt(i);
                        ItemWagePayBinding bind = DataBindingUtil.bind(viewItem);
                        bind.cbSelect.setChecked(false);
                    }
                    ids="";

                }
            }
        });
    }

    private void doSettle() {
        if (TextUtils.isEmpty(ids)){
            ToastUtil.showLong("请选中要结算的选项！");
            return;
        }
        if (true){
            L.e("ids=="+ids);
            return;
        }

        showPayType();

    }

    private void submitPay() {
        dataProvider.server.doSettle(payment,ids)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PaidResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PaidResponse> baseResponse) {
                        ToastUtil.showLong("支付成功！订单号："+baseResponse.getData().getOrderId());
                        loadData();
                    }
                });
    }

    //支付方式
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

    private void initView() {
        tvTitle.setText("工资结算");

    }
    //添加待结算列表子项
    private void bindData() {
        db.layoutContent.removeAllViews();
        if (items==null||items.size()==0){
            db.layoutBtns.setVisibility(View.GONE);
            int dimen80 = (int) getResources().getDimension(R.dimen.dp_80);
            int height = ScreenUtils.getRealHeight()- dimen80;
            View nodataView = LayoutInflater.from(this).inflate(R.layout.empty_view_nodata, null);
            nodataView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height));
            db.layoutContent.addView(nodataView);
            return;
        }
        db.layoutBtns.setVisibility(View.VISIBLE);

        for (int i = 0; i < items.size(); i++) {
            ToSettleResponse.ItemsBean itemsBean = items.get(i);
            View viewItem = LayoutInflater.from(this).inflate(R.layout.item_wage_pay, null);
            ItemWagePayBinding bind = DataBindingUtil.bind(viewItem);
            bind.setItem(itemsBean);
            db.layoutContent.addView(viewItem);
            addWorkers(viewItem,itemsBean);
            List<ToSettleResponse.ItemsBean.UsersBean> users = itemsBean.getUsers();
            if (users==null||users.size()==0){
                bind.tvSiglePay.setText("已结算");
                bind.tvSiglePay.setTextColor(ContextCompat.getColor(this,R.color.tv_gray));
                bind.tvSiglePay.setBackgroundResource(R.drawable.round20_gray_bg);
            }else {
                //单独一项进行结算
                bind.tvSiglePay.setOnClickListener(v -> {
                    String id = itemsBean.getId();
                    ids = id;
                    doSettle();
                });
            }

        }
    }
    //添加工人列表数据
    private void addWorkers(View viewItem,ToSettleResponse.ItemsBean itemsBean) {
        List<ToSettleResponse.ItemsBean.UsersBean> users = itemsBean.getUsers();
        LinearLayout layoutWorkers = viewItem.findViewById(R.id.layout_workers);
        LinearLayout layoutExpand = viewItem.findViewById(R.id.layout_expand);
        TextView tvExpand = viewItem.findViewById(R.id.tv_expand);
        for (int j = 0; j <users.size(); j++) {
            ToSettleResponse.ItemsBean.UsersBean usersBean = users.get(j);
            View viewWorker = LayoutInflater.from(this).inflate(R.layout.item_wagepay_worker, null);
            ItemWagepayWorkerBinding bind = DataBindingUtil.bind(viewWorker);
            //工价: 300/天 考勤: 3天
            bind.tvPriceDay.setText("工价: "+itemsBean.getPrice()+"/天 考勤: "+usersBean.getCheckDays()+"天");
            bind.setItem(usersBean);
            if (j>0)viewWorker.setVisibility(View.GONE);
            layoutWorkers.addView(viewWorker);
        }
        layoutExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childCount = layoutWorkers.getChildCount();
                if (childCount==0)return;
                if (layoutWorkers.getChildAt(childCount-1).getVisibility()==View.GONE){
                    TextViewUtils.setRightIcon(R.mipmap.ic_gray_arrow_up_big,tvExpand);
                    for (int i = 1; i <childCount ; i++) {
                        layoutWorkers.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }else {
                    for (int i = 1; i <childCount ; i++) {
                       layoutWorkers.getChildAt(i).setVisibility(View.GONE);
                    }
                    TextViewUtils.setRightIcon(R.mipmap.ic_gray_arrow_down_big,tvExpand);
                }
            }
        });
    }

    final String orderInfo = "";   // 订单信息
    final static int SDK_PAY_FLAG=1;
    //调起支付宝支付
    private void alipayPay() {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(WagePayActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:

                    break;
            }
            return false;
        }
    });

}