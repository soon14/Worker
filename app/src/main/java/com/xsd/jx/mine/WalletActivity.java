package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityWalletBinding;

/**
 * 钱包
 */
public class WalletActivity extends BaseBindBarActivity<ActivityWalletBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.checkboxAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxBankcard.isChecked())db.checkboxBankcard.setChecked(false);
                    if (db.checkboxCash.isChecked())db.checkboxCash.setChecked(false);
                }
            }
        });
        db.checkboxBankcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxAlipay.isChecked())db.checkboxAlipay.setChecked(false);
                    if (db.checkboxCash.isChecked())db.checkboxCash.setChecked(false);
                }
            }
        });
        db.checkboxCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxAlipay.isChecked())db.checkboxAlipay.setChecked(false);
                    if (db.checkboxBankcard.isChecked())db.checkboxBankcard.setChecked(false);
                }
            }
        });
        tvRight.setOnClickListener(v -> goActivity(PaymentsListActivity.class));
        db.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        db.tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(SetAlipayActivity.class);
            }
        });
        db.tvLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(SetLocaPayActivity.class);
            }
        });
        db.tvBankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(SetBankcardActivity.class);
            }
        });

    }

    private void initView() {
        tvTitle.setText("钱包");
        tvRight.setText("收支明细");

    }
}