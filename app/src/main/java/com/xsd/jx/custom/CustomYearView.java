package com.xsd.jx.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.YearView;

/**
 * Date: 2020/10/15
 * author: SmallCake
 * 自定义年月视图选择器
 */
public class CustomYearView extends YearView {

    private int mTextPadding;
    /**
     * 闰年字体
     */
    private Paint mLeapYearTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomYearView(Context context) {
        super(context);
        mTextPadding = dipToPx(context, 3);

        mLeapYearTextPaint.setTextSize(dipToPx(context, 12));
        mLeapYearTextPaint.setColor(0xffd1d1d1);
        mLeapYearTextPaint.setAntiAlias(true);
        mLeapYearTextPaint.setFakeBoldText(true);

    }

    @Override
    protected void onDrawMonth(Canvas canvas, int year, int month, int x, int y, int width, int height) {

        String text = getContext()
                .getResources()
                .getStringArray(com.haibin.calendarview.R.array.month_string_array)[month - 1];


        float w = getTextWidth(mMonthTextPaint, text);
        canvas.drawText(text,
                getWidth()/2 - w/2,
                getHeight()/2+mMonthTextBaseLine/2,
                mMonthTextPaint);
    }
    private float getTextWidth(Paint paint, String text) {
        return paint.measureText(text);
    }






    @Override
    protected void onDrawWeek(Canvas canvas, int week, int x, int y, int width, int height) {

    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

    }



    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
