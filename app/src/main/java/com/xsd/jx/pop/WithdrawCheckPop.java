package com.xsd.jx.pop;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.jx.custom.BlueClickableSpan;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.SpannableStringUtils;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class WithdrawCheckPop extends CenterPopupView {
    private WithdrawCountTimer mTimer;
    private String desc;
    BlueClickableSpan clickableSpan;
    public WithdrawCheckPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_withdraw_check;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        TextView tvDesc = findViewById(R.id.tv_desc);
        mTimer = new WithdrawCountTimer(tvDesc);
                tvDesc.setMovementMethod(LinkMovementMethod.getInstance());
                desc = "本次交易需要短信确认，验证码已发送至您的手机 150****8888";

         clickableSpan = new BlueClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtil.showLong("已发送验证码");
                mTimer.start();
            }
        };

        SpannableStringBuilder span = SpannableStringUtils.getBuilder(desc)
                .append("\t\t发送验证码")
                .setClickSpan(clickableSpan)
                .create();
        tvDesc.setText(span);
        EditText etCode = findViewById(R.id.et_code);
        findViewById(R.id.iv_close).setOnClickListener(v->dismiss());
        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditTextUtils.isEmpty(etCode))return;
                dismiss();
            }
        });

    }

     class WithdrawCountTimer extends CountDownTimer {

        private TextView textView;
        public WithdrawCountTimer(TextView textView) {
            super(60000, 1000);
            this.textView=textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(textView==null){
                cancel();
                return;
            }
            SpannableStringBuilder span = SpannableStringUtils.getBuilder(desc)
                    .append("\t\t")
                    .append(millisUntilFinished/1000+"s后重新验证").setForegroundColor(ContextCompat.getColor(getContext(), R.color.tv_blue))
                    .create();
            textView.setText(span);
            textView.setEnabled(false);
        }

        @Override
        public void onFinish() {
            if(textView==null){
                cancel();
                return;
            }
            SpannableStringBuilder span = SpannableStringUtils.getBuilder(desc)
                    .append("\t\t获取验证码")
                    .setClickSpan(clickableSpan)
                    .create();
            textView.setText(span);
            textView.setEnabled(true);
        }

    }
}
