package com.xsd.jx.pop;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.SoftInputUtils;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/10/29
 * author: SmallCake
 */
public class ConfirmNumPop extends CenterPopupView {
    private ConfirmListener listener;
    private String title;
    private String confirmBtnTxt;
    private int persionNum;//默认输入的人数
    private boolean noZero=true;//需要不为0，默认需要
    public interface ConfirmListener{
        void onConfirmNum(int num);
    }

    public ConfirmNumPop(@NonNull Context context,String title,String confirmBtnTxt,int persionNum,boolean noZero,ConfirmListener listener) {
        super(context);
        this.listener = listener;
        this.title = title;
        this.confirmBtnTxt = confirmBtnTxt;
        this.persionNum = persionNum;
        this.noZero = noZero;
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
        etContent.setText(persionNum+"");
        tvCancel.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> {
            if (EditTextUtils.isEmpty(etContent))return;
            int num = Integer.parseInt(etContent.getText().toString());
            if (noZero&&num<=0){
                ToastUtil.showLong("输入人数必须大于0");
                return;
            }
            listener.onConfirmNum(num);
            dismiss();
        });
        openSoft(etContent);
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        SoftInputUtils.closeSoftInput((Activity) this.getContext());
    }

    private   void openSoft(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =(InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },300);
    }
}
