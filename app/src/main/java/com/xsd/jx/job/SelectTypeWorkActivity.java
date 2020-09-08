package com.xsd.jx.job;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivitySelectTypeWorkBinding;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 选择工种
 */
public class SelectTypeWorkActivity extends BaseBindBarActivity<ActivitySelectTypeWorkBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_type_work;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initTypeWorks();
        onEvent();
    }

    private void onEvent() {
        db.tvSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("tags=="+tags.toString());
            }
        });
    }

    private void initView() {
        tvTitle.setText("选择工种");
    }
    private Set<String> tags = new HashSet<>();//标签，所有选中的标签项
    private void initTypeWorks() {
        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,DpPxUtils.dp2px(40));
        for (int i = 0; i < 27; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(i%2==0?"电工":"木工");
            checkBox.setTextColor(ContextCompat.getColorStateList(this, R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            db.layoutContent.addView(checkBox);
            int finalI = i;
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    tags.add(finalI +(finalI%2==0?"电工":"木工"));
                } else {
                    tags.remove(finalI +(finalI%2==0?"电工":"木工"));
                }
            });
        }

    }

}