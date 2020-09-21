package com.xsd.jx.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivitySetAlipayBinding;
import com.xsd.utils.EditTextUtils;

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
        db.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }

            private void confirm() {
                if (EditTextUtils.isEmpty(db.etAccount,db.etName))return;
                String account = db.etAccount.getText().toString();
                String name = db.etName.getText().toString();

                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("account", account);
                bundle.putString("name", name);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}