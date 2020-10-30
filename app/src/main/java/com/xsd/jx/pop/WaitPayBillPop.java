package com.xsd.jx.pop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.databinding.PopWaitPayBillBinding;

/**
 * Date: 2020/8/31
 * author: SmallCake
 * 账单明细
 */
public class WaitPayBillPop extends PartShadowPopupView {
    private MyGetWorkersResponse.ItemsBean item;
    public WaitPayBillPop(@NonNull Context context, MyGetWorkersResponse.ItemsBean item) {
        super(context);
        this.item = item;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_wait_pay_bill;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        PopWaitPayBillBinding db = DataBindingUtil.bind(getPopupImplView());
        db.setItem(item);
        db.tvAdvance.setText(item.getAdvanceAmount()+"元（结算已抵扣"+item.getAdvanceUsedAmount()+"元）");
        db.tvTitle.setOnClickListener(view -> dismiss());
    }
}
