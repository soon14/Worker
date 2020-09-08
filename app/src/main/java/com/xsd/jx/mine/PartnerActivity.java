package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityPartnerBinding;
import com.xsd.jx.utils.DataBindingAdapter;

public class PartnerActivity extends BaseBindBarActivity<ActivityPartnerBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_partner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("城市合伙人");
        DataBindingAdapter.bindImageRoundUrl(db.ivTop,R.mipmap.bg_partner_top,6);
    }
}