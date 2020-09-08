package com.xsd.jx.custom;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;

/**
 * Date: 2020/8/31
 * author: SmallCake
 */
public class WaitPayBillPop extends PartShadowPopupView {

    public WaitPayBillPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_wait_pay_bill;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_title).setOnClickListener(view -> dismiss());
    }
}
