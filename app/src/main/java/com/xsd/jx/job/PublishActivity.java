package com.xsd.jx.job;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.databinding.ActivityPublishBinding;
import com.xsd.jx.utils.DateFormatUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.util.Calendar;
import java.util.List;

/**
 * 我要找活
 * 1.默认使用当前用户的工种
 * 2.默认人数是1
 */
public class PublishActivity extends BaseBindBarActivity<ActivityPublishBinding> {

    private int mYear, mMonth, mDay;//开始的年月日

    private int wtId;//工种ID
    private int provinceId,cityId,districtId;//省ID,市ID,区ID
    private String startDate,endDate;//开始和结束时间
    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.layout_work_type:
                    PopShowUtils.showWorkTypeSelect(this, workTypeBean -> {
                        wtId = workTypeBean.getId();
                        db.tvWorkType.setText(workTypeBean.getTitle());
                        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).statusBarDarkFont(true).init();
                    });
                    break;
                case R.id.layout_addr:
                    PopShowUtils.showBottomAddrSelect(this, (province,city, district) -> {
                        String address = province.getName() + city.getName() + (district == null ? "" : district.getName());
                        db.tvAddr.setText(address);
                        provinceId = province.getId();
                        cityId = city.getId();
                        districtId = district == null ?0:district.getId();
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
    }

    private void push() {
        //条件判断
        if (wtId==0){
            ToastUtil.showLong("请选择工种！");
            return;
        }
        if (provinceId==0) {
            ToastUtil.showLong("请选择在哪儿上工！");
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
        if (EditTextUtils.isEmpty(db.etNum))return;

        String numStr = db.etNum.getText().toString();
        numStr = numStr.replace("人","");
        int num = Integer.parseInt(numStr);
        if (num<=0){
            ToastUtil.showLong("空闲人数需大于0！");
            return;
        }
        dataProvider.work.publish(wtId,provinceId,cityId,districtId,startDate,endDate,num)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                    }
                });
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
        return false;
    }

    private void initView() {
        tvTitle.setText("我要找活");
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        db.etNum.setText("1");

        try {
            List<WorkTypeBean> workTypes = UserUtils.getWorkTypes();
            if (workTypes!=null&&workTypes.size()>0){
                WorkTypeBean workTypeBean = workTypes.get(0);
                wtId = workTypeBean.getId();
                String title = workTypeBean.getTitle();
                db.tvWorkType.setText(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}