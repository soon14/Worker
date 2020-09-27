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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤记录
 * 日历采用CalendarView
 * 文档：https://github.com/huanghaibin-dev/CalendarView/blob/master/QUESTION_ZH.md
 */
public class SignListActivity extends BaseBindBarActivity<ActivitySignListBinding> {

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
        tvTitle.setText("考勤记录");
        java.util.Calendar c = java.util.Calendar.getInstance();
        int mYear = c.get(java.util.Calendar.YEAR);
        int mMonth = c.get(java.util.Calendar.MONTH);
        int mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        db.tvMonth.setText("("+(mMonth+1)+"月)");

    }

    private void loadData() {
        dataProvider.work.checkLog()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<CheckLogResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<CheckLogResponse> baseResponse) {
                        CheckLogResponse data = baseResponse.getData();
                        db.setItem(data);
                        List<CheckLogResponse.ItemsBean> items = data.getItems();
                        initItems(items);
                    }

                    @Override
                    protected void onErr(String err) {
                        super.onErr(err);

                    }
                });

    }

    private void initItems(List<CheckLogResponse.ItemsBean> items) {

    }

    private void onEvent() {
        //日历月份改变监听
        db.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
            }
            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
            }
        });
        db.calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                db.tvMonth.setText("("+month+"月)");
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

        int year = db.calendarView.getCurYear();
        int month = db.calendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));

        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        db.calendarView.setSchemeDate(map);



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
                db.tvMonth.setText("("+month+"月)");
                db.calendarView.scrollToCalendar(year,month,1,true);

            });
           new XPopup.Builder(this)
                    .asCustom(bottomDatePickerPop).show();
        }else {
            bottomDatePickerPop.show();
        }
    }


}