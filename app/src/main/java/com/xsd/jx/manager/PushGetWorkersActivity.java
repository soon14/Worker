package com.xsd.jx.manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityPushGetWorkersBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.SoftInputUtils;
import com.xsd.utils.ToastUtil;

import java.util.Calendar;

/**
 * 发布招工
 *  typeId        工种ID
 *  address       上工地点
 *  startDate     开始日期
 *  endDate       结束日期
 *  price         工价（元）
 *  desc          岗位说明
 *  num           所需人数
 *  isSafe        是否购买保险 0:否 1:是
 *  settleType    结算方式 1:日结 2:做完再结
 *  advanceType   结算方式 预付款类型 1:两成 2:全款 3:不预付
 *  safeAmount    保险费用
 *  advanceAmount 预付款金额
 */
public class PushGetWorkersActivity extends BaseBindBarActivity<ActivityPushGetWorkersBinding> {
    private Integer typeId;
    private String address;
    private String startDate;
    private String endDate;
    private Integer price;
    private String desc;
    private Integer num;
    private Integer isSafe;
    private Integer settleType = 1;
    private Integer advanceType = 1 ;
    private String safeAmount = "2";
    private String advanceAmount = "40";


    private int mYear, mMonth, mDay;//开始的年月日
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
        if (EditTextUtils.isEmpty(db.etPrice,db.etNum,db.etDesc))return;

        //数据填充
        price  = Integer.valueOf(db.etPrice.getText().toString());
        num = Integer.valueOf(db.etNum.getText().toString());
        desc = db.etDesc.getText().toString();
        isSafe = db.rbBuySafe.isChecked()?1:0;
        settleType = db.rbDayPay.isChecked()?1:2;
        if (db.rbPay2cost.isChecked())advanceType=1;
        if (db.rbPayAll.isChecked())advanceType=2;
        if (db.rbNoPay.isChecked())advanceType=3;
        //TODO 还差保险金额和预付款金额

        //接口提交
        dataProvider.server.publishWork(typeId,address,startDate,endDate,price,desc,num,isSafe,settleType,advanceType,safeAmount,advanceAmount)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                    }
                });
    }

    private void onEvent() {
        EditTextUtils.setTextLengthChange(db.etDesc, length -> db.tvNum.setText(length+"/200"));
        db.setClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.layout_work_type:
                        PopShowUtils.showWorkTypeSelect(PushGetWorkersActivity.this, workTypeBean -> {
                            typeId = workTypeBean.getId();
                            db.tvWorkType.setText(workTypeBean.getTitle());
                        });
                        break;
                    case R.id.layout_addr:
                        PopShowUtils.showBottomAddrSelect(PushGetWorkersActivity.this, (city, district, desc) -> {
                            address = "湖北省" + city.getName() + (district == null ? "" : district.getName()) + (TextUtils.isEmpty(desc) ? "" : desc);
                            db.tvAddr.setText(address);
                            new Handler().postDelayed(() -> SoftInputUtils.closeSoftInput(PushGetWorkersActivity.this),800);
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
            }
        });

    }

    private void showStartTime(boolean isStartTime) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        final String data = year+"年"+(month + 1) + "月" + dayOfMonth + "日";
                        if (isStartTime){
                            startDate = data;
                            db.tvStartTime.setText(data);
                            ToastUtil.showLong("请继续选择结束时间");
                            new Handler().postDelayed(() -> showStartTime(false),300);
                        }else {
                            endDate = data;
                            db.tvEndTime.setText(data);
                        }
                    }
                },
                mYear, mMonth, mDay);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }



    private void initView() {
        tvTitle.setText("发布招工");
        db.rbDayPay.setChecked(true);
        db.rbBuySafe.setChecked(true);
        db.rbPay2cost.setChecked(true);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }


}