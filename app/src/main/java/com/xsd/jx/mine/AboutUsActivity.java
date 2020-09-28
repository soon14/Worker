package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityAboutUsBinding;
import com.xsd.utils.AppUtils;

public class AboutUsActivity extends BaseBindBarActivity<ActivityAboutUsBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("关于我们");
        String versionName = AppUtils.getVersionName();
        db.tvAppVersion.setText("APP版本：V"+versionName);
    }
}