package com.xsd.jx.job;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CheckLogResponse;
import com.xsd.jx.bean.DayCheckBean;
import com.xsd.jx.databinding.ActivitySignListBinding;
import com.xsd.jx.databinding.ItemSignDescBinding;
import com.xsd.jx.utils.DateFormatUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工人端：
 * 考勤签到{@link SignActivity} >>【考勤记录】
 *
 * 如果是今天之前的日子没打卡，那么此处显示“您当天处于工期内但未考勤，这将影响您的工资结算，若忘记考勤可联系雇主修改”
 * 日历采用CalendarView
 * 文档：https://github.com/huanghaibin-dev/CalendarView/blob/master/QUESTION_ZH.md
 */
public class SignListActivity extends BaseBindBarActivity<ActivitySignListBinding> {
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mobile;
    List<DayCheckBean> items;
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
        String ym = DateFormatUtils.ym(mYear, mMonth);
        dataProvider.work.checkLog(ym)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<CheckLogResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<CheckLogResponse> baseResponse) {
                        CheckLogResponse data = baseResponse.getData();
                        db.setCheckLog(data);
                        items = data.getItems();
                        initItems();
                        //设置今天选中
                        String selectTime = DateFormatUtils.ymd(mYear,mMonth,mDay);
                        selectItem(selectTime);
                    }

                });

    }

    /**
     * 有记录的信息，以标签形式写入
     */
    private void initItems() {
        if (items==null||items.size()==0)return;
       Map<String,Calendar> calendars = new HashMap();
        for (int i = 0; i < items.size(); i++) {
            DayCheckBean itemsBean = items.get(i);
            int status = itemsBean.getStatus();// 确认状态 1:未确认 2:已确认
            String workDate = itemsBean.getWorkDate();
            String[] split = workDate.split("-");
            int day = Integer.parseInt(split[2]);
            Calendar calendar = getSchemeCalendar(mYear, mMonth, day, status==2?0xFF3B77FF:0xFF999999, status==2?"上工":"未上");
            calendars.put(calendar.toString(), calendar);
        }
        db.calendarView.setSchemeDate(calendars);
    }
    /**
     * 选中后更新底部签到信息
     */
    private void selectItem(String selectTime) {
        if (items==null||items.size()==0){
            return;
        }
        List<DayCheckBean> selectDatas = findSelect(selectTime);
        db.layoutSignDesc.removeAllViews();
        db.tvContact.setVisibility(View.GONE);
        if (selectDatas.size()==0){
            return;
        }
        db.tvContact.setVisibility(View.VISIBLE);
        for (int i = 0; i <selectDatas.size() ; i++) {
            DayCheckBean item = selectDatas.get(i);
            mobile = item.getEmployerPhone();
            View view = LayoutInflater.from(this).inflate(R.layout.item_sign_desc, null);
            ItemSignDescBinding bind = DataBindingUtil.bind(view);
            String signInTime = item.getSignInTime();
            String signOutTime = item.getSignOutTime();
            //如果上下工都没打卡，那么此处显示：您当天处于工期内但未考勤，这将影响您的工资结算，若忘记考勤可联系雇主修改
            if (TextUtils.isEmpty(signInTime)&&TextUtils.isEmpty(signOutTime)){
                TextView tv = new TextView(this);
                tv.setText("\n您当天处于工期内但未考勤，这将影响您的工资结算，若忘记考勤可联系雇主修改");
                db.layoutSignDesc.addView(tv);
                return;
            }
            db.layoutSignDesc.addView(view);
            bind.setItem(item);
            bind.ivInPic.setOnClickListener(v -> PopShowUtils.showPic(bind.ivInPic,item.getSignInPic()));
            bind.ivOutPic.setOnClickListener(v -> PopShowUtils.showPic(bind.ivOutPic,item.getSignOutPic()));
        }
    }
    private List<DayCheckBean> findSelect(String selectTime){
        List<DayCheckBean> datas = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            DayCheckBean itemsBean = items.get(i);
            String workDate = itemsBean.getWorkDate();
            if (selectTime.equals(workDate)){
                datas.add(itemsBean);
            }
        }
        return datas;
    }

    private void onEvent() {
        db.tvContact.setOnClickListener(v -> MobileUtils.callPhone(SignListActivity.this,mobile));
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
        db.calendarView.setOnMonthChangeListener((year, month) -> {
            mYear = year;
            mMonth = month;
            db.tvMonth.setText("("+month+"月)");
            db.layoutSignDesc.removeAllViews();
            db.tvContact.setVisibility(View.GONE);
            loadData();
        });

        //月份选择
        db.tvLookOtherMonth.setOnClickListener(v -> {
            PopShowUtils.showYM(SignListActivity.this, (view, year, month, dayOfMonth) -> db.calendarView.scrollToCalendar(year,month+1,1,true));
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


}