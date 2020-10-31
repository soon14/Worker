package com.xsd.jx.custom;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Date: 2020/10/31
 * author: SmallCake
 * 设置为蓝色，且不要下划线
 */
public abstract class BlueClickableSpan extends ClickableSpan {

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(Color.parseColor("#3C78FF"));
        ds.setUnderlineText(false);
    }
}