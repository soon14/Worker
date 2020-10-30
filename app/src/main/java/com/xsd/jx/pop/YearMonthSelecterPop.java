package com.xsd.jx.pop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
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
    private static int mYear;
    @Override
    protected void onCreate() {
        super.onCreate();
        Calendar ca = Calendar.getInstance();
         mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(java.util.Calendar.MONTH)+1;
        int mDay = ca.get(java.util.Calendar.DAY_OF_MONTH);
        CalendarView calendar = findViewById(R.id.calendar_view);
        calendar.showYearSelectLayout(mYear);
        calendar.setRange(mYear-3,mMonth,1,mYear,mMonth,mDay);

        TextView tvYear = findViewById(R.id.tv_year);
        ImageView ivLeft = findViewById(R.id.iv_left);
        ImageView ivRight = findViewById(R.id.iv_right);
        tvYear.setText(mYear+"年");

        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear--;
                tvYear.setText(mYear+"年");
                calendar.scrollToCalendar(mYear,1,1,true);

            }
        });
        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear++;
                tvYear.setText(mYear+"年");
                calendar.scrollToCalendar(mYear,1,1,true);
            }
        });
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

        calendar.setOnYearChangeListener(new CalendarView.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {
                tvYear.setText(year+"年");
                int maxYear = calendar.getMaxRangeCalendar().getYear();
                int minYear = calendar.getMinRangeCalendar().getYear();
                if (year==minYear){
                    ivLeft.setVisibility(INVISIBLE);
                    ivRight.setVisibility(VISIBLE);
                }else if (year==minYear&&year==maxYear){
                    ivLeft.setVisibility(INVISIBLE);
                    ivRight.setVisibility(INVISIBLE);
                }else if (year>minYear&&year<maxYear){
                    ivLeft.setVisibility(VISIBLE);
                    ivRight.setVisibility(VISIBLE);
                }else if (year>minYear&&year==maxYear){
                    ivLeft.setVisibility(VISIBLE);
                    ivRight.setVisibility(INVISIBLE);
                }

            }
        });



    }


}
