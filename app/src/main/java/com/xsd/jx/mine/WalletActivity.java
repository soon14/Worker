package com.xsd.jx.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WithdrawInfoResponse;
import com.xsd.jx.databinding.ActivityWalletBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;

/**
 * 钱包
 * 1.支付宝（账号，姓名）
 * 2.银行卡（银行账号，姓名，所属银行）
 * 3.周边事业部（事业部id）
 */
public class WalletActivity extends BaseBindBarActivity<ActivityWalletBinding> {
    private int accountType=1;//1:支付宝 2:银行卡 3:周边事业部 4:微信
    private String account;
    private String name;
    private String divisionId;
    private String bankName;
    private static final int TO_ALIPAY=0x001;
    private static final int TO_BANK_CARD=0x002;
    private static final int TO_DIVISION=0x003;
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
                }else {
                    if (!db.checkboxBankcard.isChecked()&&!db.checkboxCash.isChecked())buttonView.setChecked(true);
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
                }else {
                    if (!db.checkboxAlipay.isChecked()&&!db.checkboxCash.isChecked())buttonView.setChecked(true);
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
                }else {
                    if (!db.checkboxAlipay.isChecked()&&!db.checkboxBankcard.isChecked())buttonView.setChecked(true);
                }
            }
        });
        tvRight.setOnClickListener(v -> goActivity(BalanceLogActivity.class));
        db.tvConfirm.setOnClickListener(v -> withdraw());
        db.tvAlipay.setOnClickListener(view -> startActivityForResult(new Intent(WalletActivity.this,SetAlipayActivity.class),TO_ALIPAY));
        db.tvLoca.setOnClickListener(view -> startActivityForResult(new Intent(WalletActivity.this,SetLocaPayActivity.class),TO_DIVISION));
        db.tvBankcard.setOnClickListener(view -> startActivityForResult(new Intent(WalletActivity.this,SetBankcardActivity.class),TO_BANK_CARD));

    }
    /**
     * TODO 暂无可提现余额，待测试
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TO_ALIPAY:
                Bundle bundle = data.getExtras();
                if (bundle==null)return;
                account = bundle.getString("account");
                name = bundle.getString("name");
                db.tvAlipay.setText(account);
                break;
            case TO_DIVISION:
                Bundle bundle2 = data.getExtras();
                if (bundle2==null)return;
                divisionId = bundle2.getString("divisionId");
                break;
            case TO_BANK_CARD:
                Bundle bundle3 = data.getExtras();
                if (bundle3==null)return;
                account = bundle3.getString("account");
                name = bundle3.getString("name");
                bankName = bundle3.getString("bankName");
                db.tvBankcard.setText(name+"-"+account+"("+bankName+")");
                break;
        }
    }
}