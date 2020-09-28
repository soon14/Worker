package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.View;

import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.databinding.ActivitySetBinding;
import com.xsd.jx.utils.UserUtils;

public class SetActivity extends BaseBindBarActivity<ActivitySetBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("更多");
        db.setClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tv_user_agreement:
                        break;
                    case R.id.tv_privacy_agreement:
                        break;
                    case R.id.tv_about_us:
                        goActivity(AboutUsActivity.class);
                        break;
                    case R.id.tv_login_out:
                        loginOut();
                        break;
                }
            }
        });
    }

    private void loginOut() {
        new XPopup.Builder(this).asConfirm("退出登录", "确定要退出登录么？", () -> {
            //清除登录数据
            UserUtils.clearLoginUser();
            //跳登录,首页定位到第一项Tab
            Apollo.emit(EventStr.LOGIN_OUT);
            //关闭当前页
            finish();
        }).show();

    }
}