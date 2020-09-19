package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WithdrawInfoResponse;
import com.xsd.jx.databinding.ActivityWalletBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;

/**
 * 钱包
 */
public class WalletActivity extends BaseBindBarActivity<ActivityWalletBinding> {
    private int accountType=1;//1:支付宝 2:银行卡 3:周边事业部 4:微信
    private String account;
    private String name;
    private String divisionId;
    private String bankName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void loadData() {
        dataProvider.user.withdrawInfo()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WithdrawInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WithdrawInfoResponse> baseResponse) {
                        WithdrawInfoResponse data = baseResponse.getData();

                    }
                });
    }

    private void onEvent() {
        db.checkboxAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxBankcard.isChecked())db.checkboxBankcard.setChecked(false);
                    if (db.checkboxCash.isChecked())db.checkboxCash.setChecked(false);
                    accountType = 1;
                }
            }
        });
        db.checkboxBankcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxAlipay.isChecked())db.checkboxAlipay.setChecked(false);
                    if (db.checkboxCash.isChecked())db.checkboxCash.setChecked(false);
                    accountType = 2;
                }
            }
        });
        db.checkboxCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (db.checkboxAlipay.isChecked())db.checkboxAlipay.setChecked(false);
                    if (db.checkboxBankcard.isChecked())db.checkboxBankcard.setChecked(false);
                    accountType = 3;
                }
            }
        });
        tvRight.setOnClickListener(v -> goActivity(BalanceLogActivity.class));
        db.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraw();

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
    /**
     * 提现申请
     *  amount      提现金额
     *  accountType 支付类型 1:支付宝 2:银行卡 3:周边事业部 4:微信
     *  account     账号
     *  name        收款人姓名
     *  divisionId  事业部ID
     *  bankName    银行名称
     */
    private void withdraw() {
        String amount = db.etAmount.getText().toString();
        dataProvider.user.withdraw(amount,accountType+"",account,name,divisionId,bankName)
                .subscribe(new OnSuccessAndFailListener<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {

                    }
                });
    }

    private void initView() {
        tvTitle.setText("钱包");
        tvRight.setText("收支明细");
        name = UserUtils.getUser().getName();

    }
}