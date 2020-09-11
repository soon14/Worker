package com.xsd.jx.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.xsd.utils.popup.GoodView;


/**
 * Date: 2020/4/16
 * author: SmallCake
 */
public class TextViewUtils {
    /**
     * 设置文本左侧图片
     * @param resId
     * @param textView
     */
    public static void setLeftIcon(int resId, TextView textView) {
        if (resId==0){
            textView.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable iconDrawable = textView.getResources().getDrawable(resId);
        iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
        textView.setCompoundDrawables(iconDrawable, null, null, null);
    }
    public static void setTopIcon(int resId, TextView textView) {
        if (resId==0){
            textView.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable iconDrawable = textView.getResources().getDrawable(resId);
        iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
        textView.setCompoundDrawables(null, iconDrawable, null, null);
    }
    public static void setRightIcon(int resId, TextView textView) {
        Drawable iconDrawable = textView.getResources().getDrawable(resId);
        iconDrawable.setBounds(0, 0, iconDrawable.getMinimumWidth(), iconDrawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, iconDrawable, null);
    }
    public static void setLeftRightIcon(TextView textView,int leftRes,int rightRes) {
        Drawable iconLeft = textView.getResources().getDrawable(leftRes);
        Drawable iconRight = textView.getResources().getDrawable(rightRes);
        iconLeft.setBounds(0, 0, iconLeft.getMinimumWidth(), iconLeft.getMinimumHeight());
        iconRight.setBounds(0, 0, iconRight.getMinimumWidth(), iconRight.getMinimumHeight());
        textView.setCompoundDrawables(iconLeft, null, iconRight, null);
    }
    public static void showGood(int resId, View textView) {
        GoodView goodView = new GoodView(textView.getContext());
        goodView.setImage(resId);
        goodView.show(textView);
    }

    public static void setBold(TextView tv,boolean setBold){
        tv.getPaint().setFakeBoldText(setBold);
    }
}
