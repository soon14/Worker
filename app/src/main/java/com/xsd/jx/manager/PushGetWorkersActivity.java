package com.xsd.jx.manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.PayResult;
import com.xsd.jx.bean.PriceBean;
import com.xsd.jx.bean.PushGetWorkersResponse;
import com.xsd.jx.custom.KeyboardStatusDetector;
import com.xsd.jx.databinding.ActivityPushGetWorkersBinding;
import com.xsd.jx.utils.DateFormatUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.util.Calendar;
import java.util.Map;

/**
 *  [发布招工]
 *  typeId        工种ID
 *  address       上工地点
 *  startDate     开始日期
 *  endDate       结束日期
 *  price         工价（元）
 *  desc          岗位说明
 *  num           所需人数
 *  isSafe        是否购买保险 0:否 1:是
 *  settleType    结算方式 1:日结 2:做完再结
 *  advanceType   预付款类型 1:两成 2:全款 3:不预付
 *  safeAmount    保险费用
 *  advanceAmount 预付款金额
 *
 * 价格限制：
 1.填写的价格不得低于推荐价，如果填写了低于推荐的价格，那么在确认输入后自动变更为推荐价
 2.如果先输入了价格，再去选择工种和区域，那么自动清除已输入价格
 */
public class PushGetWorkersActivity extends BaseBindBarActivity<ActivityPushGetWorkersBinding> {
    private int typeId;//工种ID
    private int areaId;//地区ID,用于查询工价
    private String address;//用户选择的省市区
    private String startDate;
    private String endDate;
    private int price;//输入的工价
    private String desc;//岗位说明
    private int num=1;//工人数量

    private int settleType = 1;//结算方式 1:日结 2:做完再结
    private int advanceType = 1 ;//预付款类型 1:两成 2:全款 3:不预付

    private int isSafe=0;//是否购买保险 0:否 1:是
    private int safeAmount = 0;//TODO 目前保险默认为不够买

    private int advanceAmount;//预付款
    private int payment=2;//1.微信2.支付宝

    private int recommendPrice;//推荐的工价,选择工种和地区后，有最低价格限制
    private int mYear, mMonth, mDay;//开始的年月日
    private int diffDays;//时间差，需要上工天数
    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void push() {
        //条件判断
        if (typeId==0){
            ToastUtil.showLong("请选择工种！");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showLong("请选择在哪儿上工！");
            return;
        }
        if (!db.cbAgreement.isChecked()) {
            ToastUtil.showLong("请阅读并同意用工协议！");
            return;
        }
        if (TextUtils.isEmpty(startDate)) {
            ToastUtil.showLong("请选择工期开始时间！");
            return;
        }
        if (TextUtils.isEmpty(endDate)) {
            ToastUtil.showLong("请选择工期结束时间！");
            return;
        }

        if (EditTextUtils.isEmpty(db.etAddrInfo,db.etPrice,db.etNum,db.etDesc))return;
        //数据填充
        String s = db.etPrice.getText().toString();
        s = s.replace("元","");
        price  = Integer.valueOf(s);
        if (price<recommendPrice) {
            ToastUtil.showLong("价格不得低于推荐价！");
            db.etPrice.setText("");
            return;
        }
        String numStr = db.etNum.getText().toString();
        numStr = numStr.replace("人","");
        num = Integer.valueOf(numStr);
        if (num==0) {
            ToastUtil.showLong("人数不能为0！");
            db.etNum.setText("");
            return;
        }
        desc = db.etDesc.getText().toString();
        isSafe = db.rbBuySafe.isChecked()?1:0;
        settleType = db.rbDayPay.isChecked()?1:2;
        if (db.rbPay2cost.isChecked()){
            int ceil = (int) Math.ceil(price * num * diffDays * 0.2);
            advanceAmount = ceil;
            advanceType=1;
        }
        if (db.rbPayAll.isChecked()){
            advanceAmount = price*num*diffDays;
            advanceType=2;
        }
        if (db.rbNoPay.isChecked()){
            advanceAmount=0;
            advanceType=3;
        }
        String addressInfo = db.etAddrInfo.getText().toString();

        //接口提交
        dataProvider.server.publishWork(typeId,address+addressInfo,startDate,endDate,price,desc,num,isSafe,settleType,advanceType,safeAmount+"",advanceAmount,payment)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PushGetWorkersResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PushGetWorkersResponse> baseResponse) {
                        PushGetWorkersResponse data = baseResponse.getData();
                        if (advanceAmount==0){
                            ToastUtil.showLong(data.getMessage());
                            finish();
                            Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                        }else {
                            String orderString = data.getOrderString();
                            aliPay(orderString);
                        }
                    }
                });
    }

    private void onEvent() {
        EditTextUtils.setTextLengthChange(db.etDesc, editable -> db.tvNum.setText(editable.length()+"/200"));
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.layout_work_type:
                    PopShowUtils.showWorkTypeSelect(PushGetWorkersActivity.this, workTypeBean -> {
                        typeId = workTypeBean.getId();
                        db.tvWorkType.setText(workTypeBean.getTitle());
                        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).statusBarDarkFont(true).init();
                        //如果地址已经选择了，就查询推荐的工种价格
                        if (!TextUtils.isEmpty(address)){
                            searchWorkPrice();
                        }
                    });
                    break;
                case R.id.layout_addr:
                    PopShowUtils.showBottomAddrSelect(PushGetWorkersActivity.this, (city, district) -> {
                        address = "湖北省" + city.getName() + (district == null ? "" : district.getName());
                        db.tvAddr.setText(address);
                        areaId = city.getId();
                        //如果已经选择了工种，就查询推荐的工种价格
                        if (typeId>0){
                            searchWorkPrice();
                        }
                    });
                    break;
                case R.id.tv_start_time:
                    showStartTime(true);
                    break;
                case R.id.tv_end_time:
                    showStartTime(false);
                    break;
                case R.id.tv_push:
                    push();
                    break;
            }
        });
        //根据输入工价，动态改变预付款金额
        EditTextUtils.setTextLengthChange(db.etPrice, s -> {
            if (s.length()>0){
                String s1 = s.toString();
                s1 = s1.replace("元","");
                if (TextUtils.isEmpty(s1))return;
                price = Integer.parseInt(s1);
                updateAdvanceBtn();
            }
        });
        //根据输入人数，动态改变预付款金额
        EditTextUtils.setTextLengthChange(db.etNum, s -> {
            if (s.length()>0){
                String s1 = s.toString();
                s1 = s1.replace("人","");
                if (TextUtils.isEmpty(s1))return;
                num = Integer.parseInt(s1);
                updateAdvanceBtn();
            }
        });
        //根据购买保险和预付款，更新发布按钮
        db.rbBuySafe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isSafe = isChecked?1:0;
            updateBtn();
        });
        db.rgAdvancePay.setOnCheckedChangeListener((group, checkedId) -> updateBtn());


        //监听软键盘的收起，
        new KeyboardStatusDetector()
                .registerView(db.etPrice)
                .setmVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
                    @Override
                    public void onVisibilityChanged(boolean keyboardVisible) {
                        if(keyboardVisible) {
                            //Do stuff for keyboard visible
                            L.e("键盘弹起");
                        }else {
                            //Do stuff for keyboard hidden
                            L.e("键盘收起");
                            if (priceFocus&&recommendPrice>0){
                                String s = db.etPrice.getText().toString();
                                if (!TextUtils.isEmpty(s)){
                                    s = s.replace("元","");
                                    if (TextUtils.isEmpty(s))return;
                                    price = Integer.parseInt(s);
                                    if (price<recommendPrice){
                                        ToastUtil.showLong("价格不得低于推荐价"+recommendPrice+"!");
                                        db.etPrice.setText("");
                                    }
                                }
                            }
                        }
                    }
                });

        db.etPrice.setOnFocusChangeListener((v, hasFocus) -> priceFocus = hasFocus);

    }
    boolean priceFocus = false;//焦点是否在输入价格控件上

    /**
     * 更新发布按钮
     * TODO 还差保险费用，目前为统一不买保险
     */
    private void updateBtn() {
        if (isSafe==1){//如果要购买保险，计算保险费用，
            int totalSafePrice = num * safeAmount;
        }
        //预付2层
        if (db.rbPay2cost.isChecked()){
            int ceil = (int) Math.ceil(price * num*diffDays * 0.2);
            advanceAmount = ceil;
            advanceType=1;
        }
        //预付全款
        if (db.rbPayAll.isChecked()){
            advanceAmount = price*num*diffDays;
            advanceType=2;
        }
        //不预付
        if (db.rbNoPay.isChecked()){
            advanceAmount=0;
            advanceType=3;
        }
        db.tvPush.setText(advanceAmount==0?"发布":"发布(支付"+advanceAmount+"元)");
    }

    /**
     * 查询推荐的工价
     */

    private void searchWorkPrice() {
        dataProvider.server.recommendPrice(areaId,typeId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<PriceBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<PriceBean> baseResponse) {
                        PriceBean data = baseResponse.getData();
                        recommendPrice = data.getPrice();
                        db.etPrice.setHint("根据当地市场计算，推荐工价预算为"+recommendPrice+"元");
                        db.etPrice.setText("");
                    }

                    @Override
                    protected void onErr(String err) {
                        super.onErr(err);
                        db.etPrice.setHint("请填写合理的价格");
                    }
                });

    }

    /**
     * 更新预付款金额
     */
    private void updateAdvanceBtn() {
        if (TextUtils.isEmpty(startDate)||TextUtils.isEmpty(endDate))return;

        int priceAll = price * num * diffDays;
        int price2 = (int) Math.ceil(priceAll*0.2);
        db.rbPay2cost.setText("2成/"+price2+"元");
        db.rbPayAll.setText("全额/"+priceAll+"元");
        updateBtn();

    }

    private void showStartTime(boolean isStartTime) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        String data = DateFormatUtils.ymd(mYear,mMonth+1,mDay);

                        if (isStartTime){
                            startDate = data;
                            db.tvStartTime.setText(data);
                            //如果没有选择结束时间
                            if (TextUtils.isEmpty(endDate)){
                                new Handler().postDelayed(() -> showStartTime(false),300);
                            }else {
                                if (computeTime()) return;
                            }
                        }else {
                            endDate = data;
                            db.tvEndTime.setText(data);
                            //选中结束时间时，可能开始时间未选
                            if (TextUtils.isEmpty(startDate)){
                                new Handler().postDelayed(() -> showStartTime(true),300);
                            }else {
                                if (computeTime()) return;
                            }

                        }
                        updateAdvanceBtn();
                    }
                },
                mYear, mMonth, mDay);
        View header = LayoutInflater.from(this).inflate(R.layout.header_date_picker_dialog, null);
        TextView tv = header.findViewById(R.id.tv_title);
        tv.setText(isStartTime?"开始时间":"结束时间");
        datePickerDialog.setCustomTitle(header);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private boolean computeTime() {
        Calendar startCalendar = DateFormatUtils.strToCalendar(startDate);
        Calendar endCalendar = DateFormatUtils.strToCalendar(endDate);
        L.e("startCalendar==" + startCalendar.get(Calendar.DAY_OF_MONTH) + "  endCalendar==" + endCalendar.get(Calendar.DAY_OF_MONTH));
        if (endCalendar.before(startCalendar)) {
            startDate = "";
            endDate = "";
            db.tvStartTime.setText("");
            db.tvEndTime.setText("");
            ToastUtil.showLong("开始时间不能大于结束时间");
            return true;
        }
        diffDays = (int) (((endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24)) + 1);
        return false;
    }


    private void initView() {
        tvTitle.setText("发布招工");
        db.rbDayPay.setChecked(true);
        db.rbNoSafe.setChecked(true);
        db.rbPay2cost.setChecked(true);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }
    private static final int SDK_ALIPAY_FLAG = 1;
    private static final int SDK_WXPAY_FLAG = 2;
    private void aliPay(String orderInfo){
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(PushGetWorkersActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            L.e("msp", result.toString());
            Message msg = new Message();
            msg.what = SDK_ALIPAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case SDK_ALIPAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();//同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                if (TextUtils.equals(resultStatus, "9000")) {
                    PopShowUtils.showLoad(PushGetWorkersActivity.this, "结算成功，结算统计中...", popupView -> {
                        finish();
                        Apollo.emit(EventStr.UPDATE_GET_WORKERS);
                    });
                } else {
                    ToastUtil.showLong("支付失败");
                }
                break;
            }
            case SDK_WXPAY_FLAG:
                break;
            default:
                break;
        }
        return false;
    });




}