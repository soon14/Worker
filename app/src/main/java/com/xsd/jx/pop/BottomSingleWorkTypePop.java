package com.xsd.jx.pop;

import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.listener.OnWorkTypeSelectListener;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.custom.AutoNewLineLayout;

import java.util.List;

/**
 * Date: 2020/9/12
 * author: SmallCake
 * 从底部弹出的选择工种的弹框
 *
 */
public class BottomSingleWorkTypePop extends BottomPopupView {
    private BaseActivity activity;
    private OnWorkTypeSelectListener listener;
    private AutoNewLineLayout layoutContent;
    public BottomSingleWorkTypePop(@NonNull BaseActivity context,  OnWorkTypeSelectListener listener) {
        super(context);
        this.activity = context;
        this.listener = listener;
    }
    @Override
    protected int getPopupHeight() {
        return (int) (ScreenUtils.getRealHeight()*0.96f);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_select_type_work;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        layoutContent = findViewById(R.id.layout_content);
        getWorkTypeList();
        findViewById(R.id.tv_confirm).setVisibility(GONE);
    }

    private void getWorkTypeList() {
        activity.getDataProvider().work.workTypeList()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<List<WorkTypeBean>>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<WorkTypeBean>> baseResponse) {
                        List<WorkTypeBean> datas = baseResponse.getData();
                        initTypeWorks(datas);
                    }
                });
    }
    private void initTypeWorks(List<WorkTypeBean> datas) {
        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,DpPxUtils.dp2px(40));
        for (int i = 0; i < datas.size(); i++) {
            WorkTypeBean item = datas.get(i);
            CheckBox checkBox = new CheckBox(activity);
            checkBox.setText(item.getTitle());
            checkBox.setTextColor(ContextCompat.getColorStateList(activity, R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            layoutContent.addView(checkBox);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                  dismiss();
                  listener.onSelect(item);
                }
            });
        }
    }

}
