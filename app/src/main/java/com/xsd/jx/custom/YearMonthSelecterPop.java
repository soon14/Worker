package com.xsd.jx.custom;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.jx.utils.DateFormatUtils;
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
        DatePicker datePicker = findViewById(R.id.date_picker);
        Calendar mCalendar= Calendar.getInstance(); datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), null);
        //禁止弹出输入键盘
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        LinearLayout ll = (LinearLayout) datePicker.getChildAt(0);
        LinearLayout ll2 = (LinearLayout) ll.getChildAt(0);
        ll2.getChildAt(1).setVisibility(View.GONE);
        Integer month = datePicker.getMonth() + 1;
        Integer year = datePicker.getYear();
        String strMonth = (month.toString().length() == 1 ? "0" + month.toString() : month.toString());
        String strYear = year.toString();


        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("选中了=="+ DateFormatUtils.ym(year,month));
            }
        });
    }
}
