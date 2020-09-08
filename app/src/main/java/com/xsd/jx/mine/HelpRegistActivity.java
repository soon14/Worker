package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.jx.databinding.ActivityHelpRegistBinding;

/**
 * 帮工友注册
 */
public class HelpRegistActivity extends BaseBindBarActivity<ActivityHelpRegistBinding> {
    private VerifyCountTimer mTimer;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_regist;
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
                case R.id.tv_type_work:
                    break;
                case R.id.tv_confirm:
                    break;

            }
        });
        tvRight.setOnClickListener(v -> goActivity(HelpRegistListActivity.class));

    }

    private void initView() {
        tvTitle.setText("帮工友注册");
        tvRight.setText("帮注册记录");
        mTimer = new VerifyCountTimer(db.tvGetCode);

    }
}