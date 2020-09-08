package com.xsd.jx;

import android.os.Bundle;

import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.jx.databinding.ActivityLoginBinding;

/**
 * 登录页
 * 首页登录
 *
 */
public class LoginActivity extends BaseBindBarActivity<ActivityLoginBinding> {
    private VerifyCountTimer mTimer;//验证码计数器
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.tv_get_code:
                    mTimer.start();
                    break;
                case R.id.tv_user_agreement:
                    break;
                case R.id.tv_privacy_agreement:
                    break;
                case R.id.tv_wx_login:
                    break;
                case R.id.tv_login:
                    break;
            }
        });
    }

    private void initView() {
        tvTitle.setText("登录");
        ivBack.setImageResource(R.mipmap.ic_black_close);
        mTimer = new VerifyCountTimer(db.tvGetCode);
    }
}