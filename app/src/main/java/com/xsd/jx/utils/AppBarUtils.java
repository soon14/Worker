package com.xsd.jx.utils;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.xsd.utils.L;

/**
 * Date: 2020/8/31
 * author: SmallCake
 */
public class AppBarUtils {
    public static void setColorChange(AppBarLayout appBar,View viewTarget,String startColor,String endColor){
        appBar.getChildAt(0).getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            //获取AppBarLayout最大滑动距离
            final int scrollRange = appBar.getTotalScrollRange();
            appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
                //获得滑动具体，计算比例
                int scrollDistance = Math.abs(verticalOffset);
                float scrollPercentage = (float) scrollDistance/scrollRange;
                L.e("scrollPercentage=="+scrollPercentage);

                ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类
                int currentLastColor = (int) (argbEvaluator.evaluate(scrollPercentage,
                        Color.parseColor(startColor),
                        Color.parseColor(endColor)));
                viewTarget.setBackgroundColor(currentLastColor);
            });
        });
    }

    public static void setColorGrayWhite(AppBarLayout appBar,View viewTarget){
        setColorChange(appBar,viewTarget,"#f5f5f5","#ffffff");
    }

}
