package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.custom.PayTypePop;
import com.xsd.jx.databinding.ActivityWagePayBinding;
import com.xsd.jx.databinding.ItemWagePayBinding;
import com.xsd.jx.databinding.ItemWagepayWorkerBinding;
import com.xsd.jx.listener.OnPayListener;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TextViewUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * 工资结算
 */
public class WagePayActivity extends BaseBindBarActivity<ActivityWagePayBinding> {
    private String ids;
    private List<ToSettleResponse.ItemsBean> items;
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
                        builder.append(items.get(i).getId());
                        if (i<childCount-1)builder.append(",");
                    }
                    ids= builder.toString();

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

    private void onEvent() {
        db.tvPayedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataProvider.server.doSettle(ids)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                            @Override
                            protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                showPayType();
                            }
                        });
            }
        });
    }
    PayTypePop payTypePop;
    private void showPayType() {
        if (payTypePop==null){
            payTypePop = new PayTypePop(this);
            payTypePop.setListener(new OnPayListener() {
                @Override
                public void wxPay() {
                    ToastUtil.showLong("微信支付中....");
                }

                @Override
                public void aliPay() {
                    ToastUtil.showLong("支付宝支付中....");
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
    //TODO 已支付的工资，还需支付金额
    private void bindData() {
        if (items==null||items.size()==0)return;
        for (int i = 0; i < items.size(); i++) {
            ToSettleResponse.ItemsBean itemsBean = items.get(i);
            View viewItem = LayoutInflater.from(this).inflate(R.layout.item_wage_pay, null);
            ItemWagePayBinding bind = DataBindingUtil.bind(viewItem);
            bind.setItem(itemsBean);
            db.layoutContent.addView(viewItem);
            addWorkers(viewItem,itemsBean);
        }
    }

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
}