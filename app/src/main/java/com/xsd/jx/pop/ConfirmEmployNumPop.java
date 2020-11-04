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
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.UnmatchedResponse;
import com.xsd.jx.listener.OnConfirmEditEmployNumListener;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class ConfirmEmployNumPop extends CenterPopupView {
    private UnmatchedResponse item;
    private BaseActivity activity;
    private OnConfirmEditEmployNumListener listener;
    public ConfirmEmployNumPop(@NonNull BaseActivity context, UnmatchedResponse unmatchedResponse,OnConfirmEditEmployNumListener listener) {
        super(context);
        this.activity = context;
        this.item=unmatchedResponse;
        this.listener = listener;
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
        TextView tvDesc = findViewById(R.id.tv_desc);
        TextView tvIscalledDesc = findViewById(R.id.tv_iscalled_desc);
        Integer num = item.getNum();
        String phone = item.getPhone();
        Integer surplusNum = item.getSurplusNum();
        tvDesc.setText("对方有"+num+"人，您还差"+surplusNum+"人");
        tvIscalledDesc.setText("已拨打电话 确认雇佣"+surplusNum+"人");
        findViewById(R.id.layout_call).setOnClickListener(v -> MobileUtils.callPhone(activity,phone));

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
                listener.onConfirm();
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(v->dismiss());


    }
}
