package com.xsd.jx.custom;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/10/29
 * author: SmallCake
 */
public class ConfirmNumPop extends CenterPopupView {
    private ConfirmListener listener;
    private String title;
    private String confirmBtnTxt;
    public interface ConfirmListener{
        void onConfirmNum(int num);
    }

    public ConfirmNumPop(@NonNull Context context,String title,String confirmBtnTxt,ConfirmListener listener) {
        super(context);
        this.listener = listener;
        this.title = title;
        this.confirmBtnTxt = confirmBtnTxt;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_confrim_num;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvTitle = findViewById(R.id.tv_title);
        EditText etContent = findViewById(R.id.et_content);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        tvTitle.setText(title);
        tvConfirm.setText(confirmBtnTxt);
        tvCancel.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> {
            if (EditTextUtils.isEmpty(etContent))return;
            int num = Integer.parseInt(etContent.getText().toString());
            if (num<=0){
                ToastUtil.showLong("输入人数必须大于0");
                return;
            }
            listener.onConfirmNum(num);
            dismiss();

        });

    }
}
