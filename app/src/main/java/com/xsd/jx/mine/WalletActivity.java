package com.xsd.jx.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
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
    private String accountAliPay;
    private String nameAliPay;
    private String accountBank;
    private String nameBank;
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
                        UserUtils.saveUser(data);
                        db.setItem(data.getInfo());
                    }
                });
    }

    /**
     * 设置支付方式的数据
     */
    private void initPayData() {
        WithdrawInfoResponse.WxBean wx = payData.getWx();

        WithdrawInfoResponse.AliBean ali = payData.getAli();
        WithdrawInfoResponse.DivisionBean division = payData.getDivision();
        WithdrawInfoResponse.BankBean bank = payData.getBank();
        //默认选中的支付宝1
        accountAliPay = ali.getAccount();
        nameAliPay = ali.getName();
        if (!TextUtils.isEmpty(accountAliPay)){
            db.tvAlipay.setText(accountAliPay+"-"+nameAliPay);
        }
        //银行卡2
        accountBank = bank.getAccount();//银行卡号
        nameBank = bank.getName();//真实名称
        bankName = bank.getBankName();//银行名称
        if (!TextUtils.isEmpty(accountBank)){
            db.tvBankcard.setText(nameBank+"-"+accountBank+"("+bankName+")");
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
            accountAliPay = ali.getAccount();
            nameAliPay = ali.getName();
        } else {
            if (!db.checkboxBankcard.isChecked() && !db.checkboxDivision.isChecked()) {
                db.checkboxAlipay.setChecked(true);
                WithdrawInfoResponse.AliBean ali = payData.getAli();
                accountAliPay = ali.getAccount();
                nameAliPay = ali.getName();
            }
        }
    }

    private void selectBank(boolean isChecked) {
        if (isChecked) {
            if (db.checkboxAlipay.isChecked()) db.checkboxAlipay.setChecked(false);
            if (db.checkboxDivision.isChecked()) db.checkboxDivision.setChecked(false);
            accountType = 2;
            WithdrawInfoResponse.BankBean bank = payData.getBank();
            accountBank = bank.getAccount();
            nameBank = bank.getName();
            bankName = bank.getBankName();
        } else {
            if (!db.checkboxAlipay.isChecked() && !db.checkboxDivision.isChecked()) {
                db.checkboxBankcard.setChecked(true);
                WithdrawInfoResponse.BankBean bank = payData.getBank();
                accountBank = bank.getAccount();
                nameBank = bank.getName();
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
        String account="",name="";
        if (accountType==1){
            account = accountAliPay;
            name = nameAliPay;
            if (TextUtils.isEmpty(accountAliPay)){
                //请设置支付宝账号！
                ToastUtil.showLong("您还未设置支付宝账号，请在此页面填写");
                startActivityForResult(new Intent(WalletActivity.this,SetAlipayActivity.class),TO_ALIPAY);
                return;
            }
        }else if (accountType==2){
            account = accountBank;
            name = nameBank;
            if (TextUtils.isEmpty(accountBank)){
                ToastUtil.showLong("您还未添加银行卡，请在此页面填写");
                startActivityForResult(new Intent(WalletActivity.this,SetBankcardActivity.class),TO_BANK_CARD);
                return;
            }
        }else if (accountType==3){
            account = "";
            name = "";
            if (divisionId==0){
                ToastUtil.showLong("请选择事业部！");
                startActivityForResult(new Intent(WalletActivity.this,SetLocaPayActivity.class),TO_DIVISION);
                return;
            }
        }
        dataProvider.user.withdraw(amount,accountType,account,name,divisionId,bankName)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        //提现成功后刷新我的页码，余额
                        Apollo.emit(EventStr.UPDATE_USER_INFO);
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
                accountAliPay = bundle.getString("account");//支付宝账号
                nameAliPay = bundle.getString("name");//支付宝名称
                db.tvAlipay.setText(accountAliPay+"-"+nameAliPay);
                break;
            case TO_DIVISION:
                DivisionBean item= (DivisionBean) bundle.getSerializable("item");
                divisionId = item.getId();
                db.tvDivision.setText(item.getName()+"-"+item.getAddr());
                break;
            case TO_BANK_CARD:
                accountBank = bundle.getString("account");//银行账号
                nameBank = bundle.getString("name");//真实姓名
                bankName = bundle.getString("bankName");//银行名称
                db.tvBankcard.setText(nameBank+"-"+accountBank+"("+bankName+")");
                break;
        }
    }
}