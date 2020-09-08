package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivitySetBankcardBinding;

public class SetBankcardActivity extends BaseBindBarActivity<ActivitySetBankcardBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_bankcard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        tvTitle.setText("设置银行卡");
    }
}