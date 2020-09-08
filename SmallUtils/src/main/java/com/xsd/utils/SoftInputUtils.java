package com.xsd.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * MyApplication --  cq.cake.util
 * Created by Small Cake on  2017/11/29 18:59.
 */

public class SoftInputUtils {
    /**
     * 开启键盘
     * @param context
     */
    public static void openSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null)imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭键盘
     * @param context
     */
    public static void closeSoftInput(Activity context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null)imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }
    /**
     * 键盘顶起控件
     * @param activity 视图
     * @param view 被弹起的视图
     * @注意 需要设置activity的根布局文件属性
     * android:fitsSystemWindows="true"
     */
    public static void setHintKeyboardView(final Activity activity, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hintKeyboard(activity);
                return false;
            }
        });
        if (view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setHintKeyboardView(activity,viewGroup.getChildAt(i));
            }
        }
    }
    private static void hintKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
