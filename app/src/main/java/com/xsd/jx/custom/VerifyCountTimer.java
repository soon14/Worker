package com.xsd.jx.custom;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Date: 2020/8/24
 * author: SmallCake
 */
public class VerifyCountTimer extends CountDownTimer {

    private TextView textView;
    public VerifyCountTimer(TextView textView) {
        super(60000, 1000);
        this.textView=textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(textView==null){
            cancel();
            return;
        }
        textView.setText(millisUntilFinished/1000+"s");
        textView.setEnabled(false);
    }

    @Override
    public void onFinish() {
        if(textView==null){
            cancel();
            return;
        }
        textView.setText("获取验证码");
        textView.setEnabled(true);
    }

}
