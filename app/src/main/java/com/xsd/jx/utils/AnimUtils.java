package com.xsd.jx.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Date: 2020/3/17
 * author: SmallCake
 */
public class AnimUtils {
    public static void tabSelect(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.6f,1f);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1.1f,1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1.1f,1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, pvhX, pvhY);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    /**
     * 浮动动画
     * @param view
     */
    public static void floatView(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.6f,1f);
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY",  8);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, translateY);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(1000);
        animator.start();
    }
    public static void potView(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f,0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f,1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f,1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX,scaleY);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.start();
    }
    public static void scaleView(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.8f,1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.9f,1.1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.9f,1.1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha,scaleX, scaleY);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(300);
        animator.start();
    }
    public static void sunshineRotate(View view){
        Animation anim =new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(5000); // 设置动画时间
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setInterpolator(new LinearInterpolator());
        view.startAnimation(anim);
    }

    public static ObjectAnimator rotateAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0, 360);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(1000);
        animator.start();
        return animator;
    }
}
