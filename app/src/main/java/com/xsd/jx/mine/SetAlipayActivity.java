package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivitySetAlipayBinding;

public class SetAlipayActivity extends BaseBindBarActivity<ActivitySetAlipayBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_alipay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("设置支付宝");
    }
}