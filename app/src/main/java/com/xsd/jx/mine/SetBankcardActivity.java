package com.xsd.jx.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivitySetBankcardBinding;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

public class SetBankcardActivity extends BaseBindBarActivity<ActivitySetBankcardBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_bankcard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.tvBankname.setOnClickListener(v ->
                PopShowUtils.showBankName(db.tvBankname,
                        (position, text) -> db.tvBankname.setText(text))
        );

        db.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }

            private void confirm() {
                if (EditTextUtils.isEmpty(db.etAccount,db.etName))return;
                String account = db.etAccount.getText().toString();
                String name = db.etName.getText().toString();
                String bankName = db.tvBankname.getText().toString();
                if (TextUtils.isEmpty(bankName)){
                    ToastUtil.showLong("请选中所属银行！");
                    return;
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("account", account);
                bundle.putString("name", name);
                bundle.putString("bankName", bankName);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    private void initView() {
        tvTitle.setText("设置银行卡");
    }
}