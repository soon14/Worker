package com.xsd.jx.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.DivisionBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.bean.WithdrawInfoResponse;
import com.xsd.jx.databinding.ActivityWalletBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

/**
 * 钱包
 * 1.支付宝（账号，姓名）
 * 2.银行卡（银行账号，姓名，所属银行）
 * 3.周边事业部（事业部id）
 balance	integer
 总余额(分)
 liveBalance	integer
 可提现余额(分)
 frozenBalance	integer
 冻结余额(分)
 withdrawTotal	integer
 已提现总金额（分）
 */
public class WalletActivity extends BaseBindBarActivity<ActivityWalletBinding> {
    private int accountType=1;//1:支付宝 2:银行卡 3:周边事业部 4:微信
    private String account;
    private String name;
    private int divisionId;
    private String bankName;
    private static final int TO_ALIPAY=0x001;
    private static final int TO_BANK_CARD=0x002;
    private static final int TO_DIVISION=0x003;
    WithdrawInfoResponse payData;
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
    private void initView() {
        tvTitle.setText("钱包");
        tvRight.setText("收支明细");
        name = UserUtils.getUser().getName();
    }

    private void loadData() {
        dataProvider.user.withdrawInfo()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WithdrawInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WithdrawInfoResponse> baseResponse) {
                         payData = baseResponse.getData();
                        //设置支付方式的数据
                        initPayData();
                    }
                });
        dataProvider.user.info()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UserInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UserInfoResponse> baseResponse) {
                        UserInfoResponse data = baseResponse.getData();
                        UserInfo info = data.getInfo();
                        UserUtils.saveUser(info);
//                        info.setLiveBalance(1000000);
                        db.setItem(info);
                    }
                });
    }

    /**
     * 设置支付方式的数据
     * @param data
     */
    private void initPayData() {
        WithdrawInfoResponse.WxBean wx = payData.getWx();

        WithdrawInfoResponse.AliBean ali = payData.getAli();
        WithdrawInfoResponse.DivisionBean division = payData.getDivision();
        WithdrawInfoResponse.BankBean bank = payData.getBank();
        //默认选中的支付宝1
        String account1 = ali.getAccount();
        String name1 = ali.getName();
        if (!TextUtils.isEmpty(account1)){
            db.tvAlipay.setText(name1+"-"+account1);
            account = account1;
            name = name1;
        }
        //银行卡2
        String account2 = bank.getAccount();//号码
        bankName = bank.getBankName();
        String name2 = bank.getName();
        if (!TextUtils.isEmpty(account2)){
            db.tvBankcard.setText(name2+"-"+account2);
        }

        //事业部3
        divisionId = division.getId();
        String name3 = division.getName();
        String addr3 = division.getAddr();
        if (!TextUtils.isEmpty(name3)){
            db.tvDivision.setText(name3+"-"+addr3);
        }



    }

    private void onEvent() {
        db.checkboxAlipay.setOnCheckedChangeListener((buttonView, isChecked) -> selectAlipay(isChecked));
        db.checkboxBankcard.setOnCheckedChangeListener((buttonView, isChecked) -> selectBank(isChecked));
        db.checkboxDivision.setOnCheckedChangeListener((buttonView, isChecked) -> selectDivision(isChecked));

        tvRight.setOnClickListener(v -> goActivity(BalanceLogActivity.class));
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.tv_alipay:
                case R.id.tv_alipay_name:
                    startActivityForResult(new Intent(WalletActivity.this,SetAlipayActivity.class),TO_ALIPAY);
                    break;
                case R.id.tv_division:
                case R.id.tv_division_name:
                    startActivityForResult(new Intent(WalletActivity.this,SetLocaPayActivity.class),TO_DIVISION);
                    break;
                case R.id.tv_bankcard:
                case R.id.tv_bankcard_name:
                    startActivityForResult(new Intent(WalletActivity.this,SetBankcardActivity.class),TO_BANK_CARD);
                    break;
                case R.id.tv_confirm:
                    withdraw();
                    break;
            }
        });

    }


    private void selectAlipay(boolean isChecked) {
        if (isChecked) {
            if (db.checkboxBankcard.isChecked()) db.checkboxBankcard.setChecked(false);
            if (db.checkboxDivision.isChecked()) db.checkboxDivision.setChecked(false);
            accountType = 1;
            WithdrawInfoResponse.AliBean ali = payData.getAli();
            account = ali.getAccount();
            name = ali.getName();
        } else {
            if (!db.checkboxBankcard.isChecked() && !db.checkboxDivision.isChecked()) {
                db.checkboxAlipay.setChecked(true);
                WithdrawInfoResponse.AliBean ali = payData.getAli();
                account = ali.getAccount();
                name = ali.getName();
            }
        }
    }

    private void selectBank(boolean isChecked) {
        if (isChecked) {
            if (db.checkboxAlipay.isChecked()) db.checkboxAlipay.setChecked(false);
            if (db.checkboxDivision.isChecked()) db.checkboxDivision.setChecked(false);
            accountType = 2;
            WithdrawInfoResponse.BankBean bank = payData.getBank();
            account = bank.getAccount();
            name = bank.getName();
            bankName = bank.getBankName();
        } else {
            if (!db.checkboxAlipay.isChecked() && !db.checkboxDivision.isChecked()) {
                db.checkboxBankcard.setChecked(true);
                WithdrawInfoResponse.BankBean bank = payData.getBank();
                account = bank.getAccount();
                name = bank.getName();
                bankName = bank.getBankName();
            }
        }
    }
    private void selectDivision(boolean isChecked) {
        if (isChecked) {
            if (db.checkboxAlipay.isChecked()) db.checkboxAlipay.setChecked(false);
            if (db.checkboxBankcard.isChecked()) db.checkboxBankcard.setChecked(false);
            accountType = 3;
            WithdrawInfoResponse.DivisionBean division = payData.getDivision();
            divisionId = division.getId();
        } else {
            if (!db.checkboxAlipay.isChecked() && !db.checkboxBankcard.isChecked())
                db.checkboxDivision.setChecked(true);
        }
    }



    /**
     * TODO 暂无可提现余额，待测试
     *  提现申请
     *  amount      提现金额
     *  accountType 支付类型 1:支付宝 2:银行卡 3:周边事业部 4:微信
     *  account     账号
     *  name        收款人姓名
     *  divisionId  事业部ID
     *  bankName    银行名称
     */
    private void withdraw() {
        if (EditTextUtils.isEmpty(db.etAmount))return;
        int amount = Integer.parseInt(db.etAmount.getText().toString());
        if (amount==0){
            ToastUtil.showLong("提现金额需大于0");
            return;
        }
        if (accountType==1){
            if (TextUtils.isEmpty(account)){
                ToastUtil.showLong("请设置支付宝账号！");
                return;
            }
        }else if (accountType==2){
            if (TextUtils.isEmpty(account)){
                ToastUtil.showLong("请添加银行卡！");
                return;
            }
        }else if (accountType==3){
            if (divisionId==0){
                ToastUtil.showLong("请选择事业部！");
                return;
            }
        }
        dataProvider.user.withdraw(amount,accountType,account,name,divisionId,bankName)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null||data.getExtras()==null)return;
        Bundle bundle = data.getExtras();
        switch (requestCode){
            case TO_ALIPAY:
                account = bundle.getString("account");
                name = bundle.getString("name");
                db.tvAlipay.setText(account);
                break;
            case TO_DIVISION:
                DivisionBean item= (DivisionBean) bundle.getSerializable("item");
                divisionId = item.getId();
                db.tvDivision.setText(item.getName()+"-"+item.getAddr());
                break;
            case TO_BANK_CARD:
                account = bundle.getString("account");
                name = bundle.getString("name");
                bankName = bundle.getString("bankName");
                db.tvBankcard.setText(name+"-"+account+"("+bankName+")");
                break;
        }
    }
}