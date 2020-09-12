package com.xsd.jx.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.listener.OnNationSelectListener;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.custom.RadioGroupEx;

/**
 * Date: 2020/8/19
 * author: SmallCake
 */
public class BottomNationSelecterPop extends BottomPopupView {
    String nationStr = "汉族、满族、蒙古族、回族、藏族、维吾尔族、苗族、彝族、壮族、布依族、侗族、瑶族、白族、土家族、哈尼族、哈萨克族、傣族、黎族、傈僳族、佤族、畲族、高山族、拉祜族、水族、东乡族、纳西族、景颇族、柯尔克孜族、土族、达斡尔族、仫佬族、羌族、布朗族、撒拉族、毛南族、仡佬族、锡伯族、阿昌族、普米族、朝鲜族、塔吉克族、怒族、乌孜别克族、俄罗斯族、鄂温克族、德昂族、保安族、裕固族、京族、塔塔尔族、独龙族、鄂伦春族、赫哲族、门巴族、珞巴族、基诺族";
    private OnNationSelectListener listener;
    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_nation_selecter;
    }

    public BottomNationSelecterPop(@NonNull Context context,OnNationSelectListener listener) {
        super(context);
        this.listener=listener;

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
    }

    private void initView() {
        String[] split = nationStr.split("、");
        RadioGroupEx layoutNations = findViewById(R.id.layout_nations);
        int dp4 = DpPxUtils.dp2px(4);
        int dp8 = DpPxUtils.dp2px(8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp4,dp4,dp4,dp4);
        for (int i = 0; i < split.length; i++) {
            String nationName = split[i];
            RadioButton radioButton = new RadioButton(this.getContext());
            radioButton.setTextSize(16);
            radioButton.setTextColor(ContextCompat.getColorStateList(this.getContext(),R.color.text_blue_black_selecter));
            radioButton.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            radioButton.setButtonDrawable(0);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setPadding(dp8,0,dp8,0);
            radioButton.setText(nationName);
            layoutNations.addView(radioButton);
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    dismiss();
                    listener.onSelect(nationName);
                }
            });
        }
        RadioButton radioButton0 = (RadioButton) layoutNations.getChildAt(0);
        radioButton0.setChecked(true);

    }
}
