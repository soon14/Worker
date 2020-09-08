package com.xsd.jx.custom;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.xsd.jx.R;
import com.xsd.utils.L;

/**
 * Date: 2020/8/28
 * author: SmallCake
 */
public class ColorGradBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "ColorGradBehavior";
    private int PAGE_COLOR_ONE;
    private int PAGE_COLOR_TWO;
    public ColorGradBehavior() {
    }
    public ColorGradBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        PAGE_COLOR_ONE = ContextCompat.getColor(context, R.color.colorWindowBackground);
        PAGE_COLOR_TWO = ContextCompat.getColor(context, R.color.colorPrimary);
    }
    //所依赖的联动对象
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float dy = dependency.getHeight()+dependency.getY();

        float y = child.getHeight() /dy ;
        y = y>1?1:y;
        L.e("dy=="+dy+"  child.getHeight()=="+child.getHeight());
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类
        int currentLastColor = (int) (argbEvaluator.evaluate(1-y, PAGE_COLOR_ONE, PAGE_COLOR_TWO));
        child.setBackgroundColor(currentLastColor);
        return true;
    }
}
