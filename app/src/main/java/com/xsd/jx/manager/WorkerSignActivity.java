package com.xsd.jx.manager;

import android.content.Intent;
import android.os.Bundle;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkCheckResponse;
import com.xsd.jx.databinding.ActivityWorkerSignBinding;
import com.xsd.jx.utils.DateFormatUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;

/**
 * 工人考勤
 */
public class WorkerSignActivity extends BaseBindBarActivity<ActivityWorkerSignBinding> {
    private String date="";
    private int workId;
    private int userId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadBottomData();
    }
    private void initView() {
        tvTitle.setText("工人考勤");
        java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mMonth = c.get(java.util.Calendar.MONTH)+1;
        int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        date = DateFormatUtils.ymd(mYear,mMonth,mDay);
        db.tvMonth.setText("("+mMonth+"月)");
        //范围控制，不能大于当前日期月份
        db.calendarView.setRange(mYear-1,mMonth,1,mYear,mMonth,mDay);
    }

    /**
     * 根据日期查询当日我发布的工作考勤情况
     * 日期 格式 2006-01-02，可以不传，默认今日
     */
    private void loadBottomData() {
        dataProvider.server.workCheck(date)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<WorkCheckResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<WorkCheckResponse> baseResponse) {
                        WorkCheckResponse data = baseResponse.getData();
                        db.setWorkCheck(data);
                    }
                });
    }

    private void onEvent() {
        db.layoutSignList.setOnClickListener(view -> {
            Intent intent = new Intent(this, WorkerSignListActivity.class);
            intent.putExtra("date",date);
            startActivity(intent);
        });
        //日历点击事件
        db.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                int year = calendar.getYear();
                int month = calendar.getMonth();
                int day = calendar.getDay();
                date = DateFormatUtils.ymd(year,month,day);
                loadBottomData();
            }
        });
        //日历滚动选择月份事件
        db.calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                db.tvMonth.setText("("+month+"月)");
            }
        });
    }


}