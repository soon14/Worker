package com.xsd.jx.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.custom.AutoNewLineLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Date: 2020/8/31
 * author: SmallCake
 */
public class FilterPop extends PartShadowPopupView {


    public FilterPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_filter;
    }
    private Set<String> tags = new HashSet<>();//标签，所有选中的标签项
    @Override
    protected void onCreate() {
        super.onCreate();
        AutoNewLineLayout layoutContent = findViewById(R.id.layout_content);

        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, DpPxUtils.dp2px(40));
        for (int i = 0; i < 12; i++) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(i%2==0?"电工":"木工");
            checkBox.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            layoutContent.addView(checkBox);
            int finalI = i;
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    tags.add(finalI +(finalI%2==0?"电工":"木工"));
                } else {
                    tags.remove(finalI +(finalI%2==0?"电工":"木工"));
                }
            });
        }
        findViewById(R.id.tv_get_workers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("tags=="+tags.toString());
                dismiss();
            }
        });
    }

}
