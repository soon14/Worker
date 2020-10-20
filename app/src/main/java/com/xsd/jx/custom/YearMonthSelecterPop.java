package com.xsd.jx.custom;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.haibin.calendarview.CalendarView;
import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.utils.L;

import java.util.Calendar;

/**
 * Date: 2020/10/15
 * author: SmallCake
 */
public class YearMonthSelecterPop extends CenterPopupView {

    public YearMonthSelecterPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_year_month_selecter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(java.util.Calendar.MONTH)+1;
        int mDay = ca.get(java.util.Calendar.DAY_OF_MONTH);
        CalendarView calendar = findViewById(R.id.calendar_view);
        calendar.showYearSelectLayout(mYear);
        calendar.setRange(mYear-1,mMonth,1,mYear,mMonth,mDay);

        TextView tvYear = findViewById(R.id.tv_year);
        tvYear.setText(mYear+"年");
        calendar.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
                int year = calendar.getYear();
                int month = calendar.getMonth();
                L.e("选中了"+year+"年"+month+"月");
//                dismiss();
            }
        });

    }


}
