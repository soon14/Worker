package com.xsd.jx.job;

import android.os.Bundle;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CheckLogResponse;
import com.xsd.jx.custom.BottomDatePickerPop;
import com.xsd.jx.databinding.ActivitySignListBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录
 * 日历采用CalendarView
 * 文档：https://github.com/huanghaibin-dev/CalendarView/blob/master/QUESTION_ZH.md
 */
public class SignListActivity extends BaseBindBarActivity<ActivitySignListBinding> {
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mobile;
    List<CheckLogResponse.ItemsBean> items;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void initView() {
         mobile = getIntent().getStringExtra("mobile");
        tvTitle.setText("考勤记录");
        java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH)+1;
        mDay  = c.get(java.util.Calendar.DAY_OF_MONTH);
        db.tvMonth.setText("("+mMonth+"月)");
        //范围控制，不能大于当前日期月份
        db.calendarView.setRange(mYear-1,mMonth,1,mYear,mMonth,mDay);

    }

    private void loadData() {
        String ym = mYear+"-"+(mMonth<10?"0"+mMonth:mMonth);
        dataProvider.work.checkLog(ym)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<CheckLogResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<CheckLogResponse> baseResponse) {
                        CheckLogResponse data = baseResponse.getData();
                        db.setCheckLog(data);
                        items = data.getItems();
                        initItems();
                        //设置今天选中
                        String monthStr = mMonth<10?"0"+mMonth:mMonth+"";
                        String dayStr = mDay<10?"0"+mDay:mDay+"";
                        String selectTime = mYear+"-"+monthStr+"-"+dayStr;
                        selectItem(selectTime);
                    }

                    @Override
                    protected void onErr(String err) {
                        super.onErr(err);

                    }
                });

    }

    /**
     * 有记录的信息，以标签形式写入
     * @param items
     */
    private void initItems() {
        if (items==null||items.size()==0)return;
       Map<String,Calendar> calendars = new HashMap();
        for (int i = 0; i < items.size(); i++) {
            CheckLogResponse.ItemsBean itemsBean = items.get(i);
            int status = itemsBean.getStatus();// 0:未确认 1:已确认
            String workDate = itemsBean.getWorkDate();
            String[] split = workDate.split("-");
            int day = Integer.parseInt(split[2]);
            L.e(mYear+"-"+mMonth+"-"+day);
            Calendar calendar = getSchemeCalendar(mYear, mMonth, day, status==1?0xFF3B77FF:0xFF999999, status==1?"上工":"未上");
            calendars.put(calendar.toString(), calendar);
        }
        db.calendarView.setSchemeDate(calendars);
    }
    /**
     * 选中后更新底部签到信息
     */
    private void selectItem(String selectTime) {
        if (items==null||items.size()==0){
            db.setItem(null);
            return;
        }
        CheckLogResponse.ItemsBean select = findSelect(selectTime);
        db.setItem(select);
    }
    private CheckLogResponse.ItemsBean findSelect(String selectTime){
        for (int i = 0; i < items.size(); i++) {
            CheckLogResponse.ItemsBean itemsBean = items.get(i);
            int status = itemsBean.getStatus();// 0:未确认 1:已确认
            String workDate = itemsBean.getWorkDate();
            if (selectTime.equals(workDate)){
                return itemsBean;
            }
        }
        return null;
    }

    private void onEvent() {
        db.tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileUtils.callPhone(SignListActivity.this,mobile);
            }
        });
        //日历月份改变监听
        db.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
            }
            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                if (isClick){
                    int year = calendar.getYear();
                    int month = calendar.getMonth();
                    int day = calendar.getDay();

                    String monthStr = month<10?"0"+month:month+"";
                    String dayStr = day<10?"0"+day:day+"";
                    String selectTime = year+"-"+monthStr+"-"+dayStr;
                    selectItem(selectTime);
                    L.e(selectTime);
                }
            }
        });
        db.calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                mYear = year;
                mMonth = month;
                db.tvMonth.setText("("+month+"月)");
                loadData();
            }
        });

        //月份选择
        db.tvLookOtherMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectYearMonth();
            }
        });

        //设置日期拦截事件
        db.calendarView.setOnCalendarInterceptListener(new CalendarView.OnCalendarInterceptListener() {
            @Override
            public boolean onCalendarIntercept(Calendar calendar) {
                //这里写拦截条件，返回true代表拦截，尽量以最高效的代码执行
                return !calendar.isCurrentMonth();
            }

            @Override
            public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {

            }
        });


    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
    private BottomDatePickerPop bottomDatePickerPop;
    private void showSelectYearMonth() {
        if (bottomDatePickerPop==null){
            bottomDatePickerPop = new BottomDatePickerPop(this);
            bottomDatePickerPop.setListener((year, month) ->{
                mYear = year;
                mMonth = month;
                db.tvMonth.setText("("+month+"月)");
                db.calendarView.scrollToCalendar(year,month,1,true);
                loadData();

            });
           new XPopup.Builder(this)
                    .asCustom(bottomDatePickerPop).show();
        }else {
            bottomDatePickerPop.show();
        }
    }


}