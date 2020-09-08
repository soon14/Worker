package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.custom.PayTypePop;
import com.xsd.jx.databinding.ActivityWagePayBinding;
import com.xsd.jx.listener.OnPayListener;
import com.xsd.jx.utils.TextViewUtils;
import com.xsd.utils.ToastUtil;

/**
 * 工资结算
 */
public class WagePayActivity extends BaseBindBarActivity<ActivityWagePayBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wage_pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.tvPayedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPayType();
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
        for (int i = 0; i < 5; i++) {
            View viewItem = LayoutInflater.from(this).inflate(R.layout.item_wage_pay, null);
            db.layoutContent.addView(viewItem);
            addWorkers(viewItem);
        }

    }

    private void addWorkers(View viewItem) {
        LinearLayout layoutWorkers = viewItem.findViewById(R.id.layout_workers);
        LinearLayout layoutExpand = viewItem.findViewById(R.id.layout_expand);
        TextView tvExpand = viewItem.findViewById(R.id.tv_expand);
        for (int j = 0; j <3; j++) {
            View viewWorker = LayoutInflater.from(this).inflate(R.layout.item_wagepay_worker, null);
            if (j>0){
                viewWorker.setVisibility(View.GONE);
            }
            layoutWorkers.addView(viewWorker);
        }
        layoutExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childCount = layoutWorkers.getChildCount();
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