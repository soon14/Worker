package com.xsd.jx.custom;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.jx.listener.OnSortClickListener;

/**
 * Date: 2020/8/31
 * author: SmallCake
 */
public class SortPop extends PartShadowPopupView {
    private OnSortClickListener listener;

    public void setListener(OnSortClickListener listener) {
        this.listener = listener;
    }

    public SortPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_sort;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        int colorUnSelected = ContextCompat.getColor(getContext(), R.color.tv_black);
        int colorSelected = ContextCompat.getColor(getContext(), R.color.tv_blue);

        TextView tvDefault = findViewById(R.id.tv_default);
        TextView tvAge = findViewById(R.id.tv_age);
        TextView tvExperience = findViewById(R.id.tv_experience);
        TextView tvLoca = findViewById(R.id.tv_loca);
        tvDefault.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                tvDefault.setTextColor(colorSelected);
                tvAge.setTextColor(colorUnSelected);
                tvExperience.setTextColor(colorUnSelected);
                tvLoca.setTextColor(colorUnSelected);
                if (listener!=null)listener.onSortClick(0);
            }
        });
        tvAge.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                tvDefault.setTextColor(colorUnSelected);
                tvAge.setTextColor(colorSelected);
                tvExperience.setTextColor(colorUnSelected);
                tvLoca.setTextColor(colorUnSelected);
                if (listener!=null)listener.onSortClick(1);
            }
        });
        tvExperience.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                tvDefault.setTextColor(colorUnSelected);
                tvAge.setTextColor(colorUnSelected);
                tvExperience.setTextColor(colorSelected);
                tvLoca.setTextColor(colorUnSelected);
                if (listener!=null)listener.onSortClick(2);
            }
        });
        tvLoca.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                tvDefault.setTextColor(colorUnSelected);
                tvAge.setTextColor(colorUnSelected);
                tvExperience.setTextColor(colorUnSelected);
                tvLoca.setTextColor(colorSelected);
                if (listener!=null)listener.onSortClick(3);
            }
        });
    }
}
