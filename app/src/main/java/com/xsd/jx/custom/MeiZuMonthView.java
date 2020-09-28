package com.xsd.jx.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * Date: 2020/8/21
 * author: SmallCake
 */
public class MeiZuMonthView extends MonthView {

    /**
     * 自定义魅族标记的文本画笔
     */
    private Paint mTextPaint = new Paint();
    private Paint mTextWhitePaint = new Paint();

    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();
    private float mRadio;
    private int mPadding;
    private float mSchemeBaseLine;

    public MeiZuMonthView(Context context) {
        super(context);

        mTextPaint.setTextSize(dipToPx(context, 8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setTextSize(dipToPx(context,10));
        //选中后的白色
        mTextWhitePaint.setTextSize(dipToPx(context, 10));
        mTextWhitePaint.setColor(0xffffffff);
        mTextWhitePaint.setStyle(Paint.Style.FILL);
        mTextWhitePaint.setTextAlign(Paint.Align.CENTER);
        mTextWhitePaint.setFakeBoldText(true);

        mRadio = dipToPx(getContext(), 7);
        mPadding = dipToPx(getContext(), 4);
        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);

//        //兼容硬件加速无效的代码
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
//        //4.0以上硬件加速会导致无效
//        mSchemeBasicPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
    }

//    @Override
//    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelectedPre, boolean isSelectedNext) {
//        mSelectedPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(x+ mItemWidth/2  , y + mItemHeight/2,mItemWidth/2,mSelectedPaint);
//        return true;
//    }
//
//    @Override
//    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
//        mSchemeBasicPaint.setColor(calendar.getSchemeColor());
//
//        canvas.drawCircle(x + mItemWidth - mPadding - mRadio / 2, y + mPadding + mRadio, mRadio, mSchemeBasicPaint);
//
//        canvas.drawText(calendar.getScheme(),
//                x + mItemWidth - mPadding - mRadio / 2 - getTextWidth(calendar.getScheme()) / 2,
//                y + mPadding + mSchemeBaseLine, mTextPaint);
//    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        mSelectedPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x+ mItemWidth/2  , y + mItemHeight/2,mItemWidth/2,mSelectedPaint);
        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        mSchemeBasicPaint.setColor(calendar.getSchemeColor());
//        canvas.drawCircle(x + mItemWidth - mPadding - mRadio / 2, y + mPadding + mRadio, mRadio, mSchemeBasicPaint);
//        canvas.drawText(calendar.getScheme(),
//                x + mItemWidth - mPadding - mRadio / 2 - getTextWidth(calendar.getScheme()) / 2,
//                y + mPadding + mSchemeBaseLine, mTextPaint);
//        int cx = x + mItemWidth / 2;
//        canvas.drawText(calendar.getScheme(), cx, mTextBaseLine + y + mItemHeight / 10, mCurMonthLunarTextPaint);
    }

    private float getTextWidth(String text) {
        return mTextPaint.measureText(text);
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;

        boolean isInRange = isInRange(calendar);//日期是否在范围内，超出范围的可以置灰
//
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
            String scheme = calendar.getScheme();
            if (!TextUtils.isEmpty(scheme))canvas.drawText(scheme, cx, mTextBaseLine + y + mItemHeight / 10, mTextWhitePaint);

        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, calendar.isCurrentMonth()?mCurMonthTextPaint:mOtherMonthTextPaint);
            String scheme = calendar.getScheme();
            if (!TextUtils.isEmpty(scheme))canvas.drawText(scheme, cx, mTextBaseLine + y + mItemHeight / 10, calendar.isCurrentMonth()?mSchemeBasicPaint:mTextWhitePaint);
       } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurMonthTextPaint :
                            calendar.isCurrentMonth() && isInRange ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
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
