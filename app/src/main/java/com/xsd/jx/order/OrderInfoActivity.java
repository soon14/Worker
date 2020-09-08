package com.xsd.jx.order;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.databinding.ActivityOrderInfoBinding;

/**
 * 报名中
 * 待开工
 * 工期中
 * 待结算
 * 待评价
 * 全部订单：除了上面5个还有：已完成，已取消，已招满
 */
public class OrderInfoActivity extends BaseBindActivity<ActivityOrderInfoBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.ivBack.setOnClickListener(v->finish());
    }

    private void initView() {
        db.toolbar.setTitleTextColor(Color.WHITE);
        db.toolbar.setSubtitleTextColor(Color.WHITE);
        int type = getIntent().getIntExtra("type", 0);
        switch (type){
            case 0:
                db.tvStatus.setText("报名中");
                db.tvDesc.setText("请耐心等待雇主确认，确认通过后即可上工");
                db.tvCancelApply.setVisibility(View.VISIBLE);
                db.tvContactManager.setVisibility(View.GONE);
                db.layoutCenter.setVisibility(View.GONE);
                db.tvConfirmTime.setVisibility(View.GONE);
                break;
            case 1:
                db.tvStatus.setText("待开工");
                db.tvDesc.setText("请按要求完成工作后才能结算工资");
                break;
            case 2:
                db.tvStatus.setText("工期中");
                db.tvDesc.setText("请按要求完成工作后才能结算工资");
                break;
            case 3:
                db.tvStatus.setText("待结算");
                db.tvDesc.setText("您已完成工作，雇主正在为您打款，您辛苦了");
                break;
            case 4:
                db.tvStatus.setText("待评价");
                db.tvDesc.setText("您的工资已到账，快评价下对方吧");
                db.tvCommentManager.setVisibility(View.VISIBLE);
                db.tvPayedTime.setVisibility(View.VISIBLE);
                db.tvContactManager.setVisibility(View.GONE);
                break;
            case 5:
                db.tvStatus.setText("已完成");
                db.tvDesc.setText("您的工资已到账，感谢您的付出");
                db.tvPayedTime.setVisibility(View.VISIBLE);
                setTopColor();
                break;
            case 6:
                db.tvStatus.setText("已取消");
                db.tvCancelTime.setVisibility(View.VISIBLE);
                db.tvConfirmTime.setVisibility(View.GONE);
                db.tvDesc.setText("更好的工作在等您");
                setTopColor();
                break;
            case 7:
                db.tvStatus.setText("已招满");
                db.tvDesc.setText("快去看看其他工作吧");
                db.layoutCenter.setVisibility(View.GONE);
                db.tvConfirmTime.setVisibility(View.GONE);
                setTopColor();
                break;

        }
    }

    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
        db.tvWorkPrice.setTextColor(gray);
        db.tvPayedScale.setTextColor(gray);
        db.tvBuySafe.setTextColor(gray);
        db.tvPayedScale.setBackgroundResource(R.drawable.round3_gray_bg);
        db.tvBuySafe.setBackgroundResource(R.drawable.round3_gray_bg);
    }
}