package com.xsd.jx.pop;

/**
 * Date: 2020/8/22
 * author: SmallCake
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.aigestudio.wheelpicker.WheelPicker;
import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 底部年月时间选择弹出窗
 * 请使用{@link com.xsd.jx.utils.PopShowUtils#showYM(BaseActivity, DatePickerDialog.OnDateSetListener)}
 */
@Deprecated
public class BottomDatePickerPop extends BottomPopupView {
    private WheelPicker wheelYear;
    private WheelPicker wheelMonth;
    private int selectYear;
    private int selectMonth;

    public BottomDatePickerPop(@NonNull Context context) {
        super(context);
    }

    private onSelectYearMonth listener;

    public void setListener(onSelectYearMonth listener) {
        this.listener = listener;
    }

    public interface onSelectYearMonth {
        void selectYearMonth(int year, int month);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_date_picker;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        wheelYear = findViewById(R.id.wheel_year);
        wheelMonth = findViewById(R.id.wheel_month);
        initYearMonth();
    }

    private void initYearMonth() {
        Calendar now = Calendar.getInstance();
        int yearNum = now.get(Calendar.YEAR);
        int monthNum = now.get(Calendar.MONTH);//默认月份会少1
        selectYear = yearNum;
        selectMonth = monthNum + 1;

        List<String> years = getYearNum(yearNum);
        List<String> months = getMonthNum(monthNum);
        wheelYear.setData(years);
        wheelMonth.setData(months);


        initWheelSet(wheelYear);
        initWheelSet(wheelMonth);

        new Handler().postDelayed(() -> {
            //设置默认选中位置
            wheelYear.setSelectedItemPosition(years.size()-1);
            wheelMonth.setSelectedItemPosition(monthNum);
        },300);



        wheelYear.setOnItemSelectedListener((picker, data, position) -> {
            String year = (String) data;
            L.e(year);
            selectYear = Integer.parseInt(year.substring(0, year.length() - 1));
        });
        wheelMonth.setOnItemSelectedListener((picker, data, position) -> {
            String month = (String) data;
            L.e(month);
            selectMonth = Integer.parseInt(month.substring(0, month.length() - 1));
        });

        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.selectYearMonth(selectYear, selectMonth);
        });
    }

    private List<String> getYearNum(int yearNum) {
        List<String> data = new ArrayList<>();
        for (int i = yearNum - 1; i <= yearNum; i++) {
            data.add(i + "年");
        }
        return data;
    }

    private List<String> getMonthNum(int monthNum) {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            data.add(i + "月");
        }
        return data;
    }

    private void initWheelSet(WheelPicker wheel) {
        wheel.setAtmospheric(true);//未选中项进行高斯模糊效果
        wheel.setCurved(true);//圆弧滚轮效果
        wheel.setItemSpace(DpPxUtils.dp2px(28));
        wheel.setItemTextColor(ContextCompat.getColor(this.getContext(), R.color.tv_gray));
        wheel.setSelectedItemTextColor(ContextCompat.getColor(this.getContext(), R.color.tv_black));
        wheel.setItemTextSize(DpPxUtils.dp2px(16));
        wheel.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
