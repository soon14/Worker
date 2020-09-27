package com.xsd.jx;

import android.os.Bundle;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.LoginUserResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.jx.databinding.ActivityLoginBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

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
                    sendSms();
                    break;
                case R.id.tv_user_agreement:
                    break;
                case R.id.tv_privacy_agreement:
                    break;
                case R.id.tv_wx_login:
                    ToastUtil.showLong("开发中...请用其他方式登录！");
                    break;
                case R.id.tv_login:
                    loginBySms();
                    break;
            }
        });
    }

    private void loginBySms() {
        if (EditTextUtils.isEmpty(db.etMobile,db.etCode))return;
        String mobile = db.etMobile.getText().toString();
        String code = db.etCode.getText().toString();
        dataProvider.site.loginBySms(mobile,code,"")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<LoginUserResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginUserResponse> baseResponse) {
                        LoginUserResponse data = baseResponse.getData();
                        UserUtils.saveLoginUser(data);
                        finish();
                        Apollo.emit(EventStr.LOGIN_SUCCESS);
                    }
                });
    }



    private void sendSms() {
        if (EditTextUtils.isEmpty(db.etMobile))return;
        String mobile = db.etMobile.getText().toString();
        dataProvider.site.sendSms(mobile,"login")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> messageBeanBaseResponse) {
                        ToastUtil.showLong(messageBeanBaseResponse.getData().getMessage());
                        mTimer.start();
                    }
                });

    }

    private void initView() {
        tvTitle.setText("登录");
        ivBack.setImageResource(R.mipmap.ic_black_close);
        mTimer = new VerifyCountTimer(db.tvGetCode);
    }
}