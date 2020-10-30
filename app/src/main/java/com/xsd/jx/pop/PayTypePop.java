package com.xsd.jx.pop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.jx.listener.OnPayListener;

/**
 * Date: 2020/9/1
 * author: SmallCake
 */
public class PayTypePop extends CenterPopupView {
    private OnPayListener listener;
    private int payType;//0微信1支付宝

    public void setListener(OnPayListener listener) {
        this.listener = listener;
    }

    public PayTypePop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_pay_type;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        AppCompatCheckBox wxPay = findViewById(R.id.checkbox_wx);
        AppCompatCheckBox aliPay = findViewById(R.id.checkbox_alipay);
        wxPay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                aliPay.setChecked(false);
                payType=0;
            }
        });
        aliPay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                wxPay.setChecked(false);
                payType=1;
            }
        });
        findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            dismiss();
            if (listener==null)return;
            switch (payType){
                case 0:
                    listener.wxPay();
                    break;
                case 1:
                    listener.aliPay();
                    break;
            }
        });
        findViewById(R.id.tv_title).setOnClickListener(view -> dismiss());

    }
}
