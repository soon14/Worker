package com.xsd.jx.custom;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.listener.OnWorkTypeSelectListener;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.custom.RadioGroupEx;

import java.util.List;

/**
 * Date: 2020/8/31
 * author: SmallCake
 * 工种筛选pop
 */
public class FilterPop extends PartShadowPopupView {
    private OnWorkTypeSelectListener listener;
    private List<WorkTypeBean> workTypes;//工种筛选数据

    public void setListener(OnWorkTypeSelectListener listener) {
        this.listener = listener;
    }

    public FilterPop(@NonNull Context context, List<WorkTypeBean> workTypes) {
        super(context);
        this.workTypes = workTypes;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_filter;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        RadioGroupEx layoutContent = findViewById(R.id.layout_content);
        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, DpPxUtils.dp2px(40));
        layoutParams.setMarginStart(DpPxUtils.dp2px(8));
        for (int i = 0; i < workTypes.size(); i++) {
            WorkTypeBean item = workTypes.get(i);
            RadioButton checkBox = new RadioButton(getContext());
            checkBox.setText(item.getTitle());
            checkBox.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            layoutContent.addView(checkBox);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                dismiss();
                if (listener!=null&&isChecked)listener.onSelect(item);
            });
        }

    }

}
