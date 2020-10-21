package com.xsd.jx.manager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.DayCheckBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserMonthLogResponse;
import com.xsd.jx.bean.WorkCheckLogResponse;
import com.xsd.jx.custom.BottomDatePickerPop;
import com.xsd.jx.databinding.ActivityWorkerSignInfoBinding;
import com.xsd.jx.databinding.ItemSignDescBinding;
import com.xsd.jx.utils.DateFormatUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  工人考勤{@link WorkerSignActivity}>>考勤记录{@link WorkerSignListActivity}>>【记录明细】
 * 获取用户整月的考勤记录
 * 根据上工记录获取用户整月的上工记录
 */
public class WorkerSignInfoActivity extends BaseBindBarActivity<ActivityWorkerSignInfoBinding> {
    private int workId;
    private int userId;
    private String yearMonth = "";//月份 格式 2020-01，可以不传，默认当月
    private String mobile;//联系对方的电话号码
    private String userName;
    private int mYear;
    private int mMonth;
    private int mDay;
    private List<DayCheckBean> items;
    private String selectTime;//当前选中的年月日

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_sign_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void initView() {
        tvTitle.setText("记录明细");
        workId = getIntent().getIntExtra("workId", 0);
        yearMonth = getIntent().getStringExtra("yearMonth");
        WorkCheckLogResponse.ItemsBean item = (WorkCheckLogResponse.ItemsBean) getIntent().getSerializableExtra("item");
        userName = item.getUserName();
        db.tvWorkerName.setText(userName);
        userId = item.getUserId();
        mobile = item.getMobile();

        java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH) + 1;
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        db.tvMonth.setText("(" + mMonth + "月)");
        //范围控制，不能大于当前日期月份
        db.calendarView.setRange(mYear - 1, mMonth, 1, mYear, mMonth, mDay);
        //设置今天选中
        selectTime = DateFormatUtils.ymd(mYear, mMonth, mDay);

    }

    /**
     * 获取用户整月的考勤记录
     */
    private void loadData() {
        dataProvider.server.userCheckLogByMonth(workId, userId, yearMonth)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UserMonthLogResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UserMonthLogResponse> baseResponse) {
                        UserMonthLogResponse data = baseResponse.getData();
                        db.setUserMonthLog(data);
                        items = data.getItems();
                        initItems();
                        selectItem();
                    }
                });
    }

    private void initItems() {
        if (items == null || items.size() == 0) return;
        Map<String, Calendar> calendars = new HashMap();
        for (int i = 0; i < items.size(); i++) {
            DayCheckBean itemsBean = items.get(i);
            int status = itemsBean.getStatus();// 确认状态 1:未确认 2:已确认
            String workDate = itemsBean.getWorkDate();
            String[] split = workDate.split("-");
            int day = Integer.parseInt(split[2]);
            L.e(mYear + "-" + mMonth + "-" + day);
            Calendar calendar = getSchemeCalendar(mYear, mMonth, day, status == 2 ? 0xFF3B77FF : 0xFF999999, status == 2 ? "上工" : "未上");
            calendars.put(calendar.toString(), calendar);
        }
        db.calendarView.setSchemeDate(calendars);
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

    /**
     * 选中后更新底部签到信息
     */
//    private void selectItem() {
//        if (items == null || items.size() == 0) {
//            db.setItem(null);
//        }
//        DayCheckBean select = findSelect(selectTime);
//        db.setItem(select);
//        db.tvEditLog.setVisibility(select == null ? View.INVISIBLE : View.VISIBLE);
//        SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(userName);
//        if (select == null){
//            builder.append("\n\n该工人当天没有考勤记录")
//                    .setProportion(0.83F)
//                    .setForegroundColor(ContextCompat.getColor(this, R.color.tv_gray));
//        }
//        db.tvWorkerName.setText(builder.create());
//    }
    /**
     * 选中后更新底部签到信息
     */
    private void selectItem() {
        if (items==null||items.size()==0){
            return;
        }
        List<DayCheckBean> selectDatas = findSelect(selectTime);
        db.layoutSignDesc.removeAllViews();
        if (selectDatas.size()==0){
            return;
        }
        for (int i = 0; i <selectDatas.size() ; i++) {
            DayCheckBean item = selectDatas.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_sign_desc, null);
            ItemSignDescBinding bind = DataBindingUtil.bind(view);
            bind.setItem(item);
            bind.ivInPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopShowUtils.showPic(bind.ivInPic,item.getSignInPic());
                }
            });
            bind.ivOutPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopShowUtils.showPic(bind.ivOutPic,item.getSignOutPic());
                }
            });
            db.layoutSignDesc.addView(view);
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
        db.setClicklistener(view -> {
            switch (view.getId()) {
                case R.id.tv_edit_log:
                    showConfrimSign();
                    break;
                case R.id.tv_contact://联系对方
                    MobileUtils.callPhone(WorkerSignInfoActivity.this, mobile);
                    break;
            }
        });
        //月份选择
        db.tvLookOtherMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSelectYearMonth();
                PopShowUtils.showYM(WorkerSignInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        db.calendarView.scrollToCalendar(year,month+1,1,true);
                    }
                });
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
        //日历日期选中事件
        db.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                if (isClick) {
                    int year = calendar.getYear();
                    int month = calendar.getMonth();
                    int day = calendar.getDay();
                    selectTime = DateFormatUtils.ymd(year, month, day);
                    selectItem();
                }
            }
        });

    }

    private BottomDatePickerPop bottomDatePickerPop;

    private void showSelectYearMonth() {
        if (bottomDatePickerPop == null) {
            bottomDatePickerPop = new BottomDatePickerPop(this);
            bottomDatePickerPop.setListener((year, month) -> {
                mYear = year;
                mMonth = month;
                db.tvMonth.setText("(" + month + "月)");
                db.calendarView.scrollToCalendar(year, month, 1, true);
                yearMonth = DateFormatUtils.ym(mYear, mMonth);
                loadData();

            });
            new XPopup.Builder(this)
                    .asCustom(bottomDatePickerPop).show();
        } else {
            bottomDatePickerPop.show();
        }
    }


    private void showConfrimSign() {
        new XPopup.Builder(this)
                .asConfirm("修改结果",
                        userName + " " + selectTime + " 属于上工期间，当天未完成考勤，您是否确定将TA的考勤记录修改为正常？",
                        "暂不修改",
                        "确定",
                        () -> {
                            dataProvider.server.helpCheck(workId, userId, selectTime)
                                    .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                                        @Override
                                        protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                            ToastUtil.showLong("已改为正常！");
                                            db.tvEditLog.setText("已确认考勤");
                                            db.tvEditLog.setTextColor(ContextCompat.getColor(WorkerSignInfoActivity.this, R.color.tv_gray));
                                            db.tvEditLog.setBackgroundResource(R.drawable.round6_gray_bg);
                                            loadData();
                                            //刷新考勤记录页面
                                            Apollo.emit(EventStr.UPDATE_WORK_CHECK_LOG);
                                            Apollo.emit(EventStr.UPDATE_WORK_CHECK);
                                        }
                                    });

                        },
                        null,
                        false, R.layout.dialog_tips)
                .show();

    }
}