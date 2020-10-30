package com.xsd.jx.pop;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class ConfirmEmployNumPop extends CenterPopupView {
    public ConfirmEmployNumPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_confrim_employ_num;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        CheckBox cbAgreement = findViewById(R.id.cb_agreement);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tvConfirm.setClickable(true);
                    tvConfirm.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                    tvConfirm.setBackgroundResource(R.drawable.round6_blue_bg);
                }else {
                    tvConfirm.setClickable(false);
                    tvConfirm.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_gray));
                    tvConfirm.setBackgroundResource(R.drawable.round6_gray_bg);
                }
            }
        });
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLong("确认雇佣");
                dismiss();
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(v->dismiss());


    }
}
