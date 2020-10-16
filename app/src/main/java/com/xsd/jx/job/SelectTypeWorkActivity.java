package com.xsd.jx.job;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.databinding.ActivitySelectTypeWorkBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

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
        getWorkTypeList();
        onEvent();
    }

    private void getWorkTypeList() {
            dataProvider.work.workTypeList()
                    .subscribe(new OnSuccessAndFailListener<BaseResponse<List<WorkTypeBean>>>() {
                        @Override
                        protected void onSuccess(BaseResponse<List<WorkTypeBean>> baseResponse) {
                            List<WorkTypeBean> datas = baseResponse.getData();
                            initTypeWorks(datas);
                        }
                    });
    }

    private void onEvent() {
        db.tvSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String ids = tags.toString()
//                        .replace("[","")
//                        .replace("]","")
//                        .replace(" ","")
//                        .trim();
//                L.e("ids=="+ids);
//                if (TextUtils.isEmpty(ids)){
//                    ToastUtil.showLong("请选择您的工种！");
//                    return;
//                }
//                submit(ids);
            }


        });
    }

    private void submit(String ids) {
        dataProvider.work.workTypeSubmitChoice(ids)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        UserInfo user = UserUtils.getUser();
                        user.setChooseWork(true);
                        UserUtils.saveUser(user);
                        //由于登录已经推荐了工种，选择工种后不再推荐
                        Apollo.emit(EventStr.SHOW_PUSH_JOB);
                    }
                });
    }

    private void initView() {
        tvTitle.setText("选择工种");
    }
//    private Set<String> tags = new HashSet<>();//标签，所有选中的标签项
    private void initTypeWorks( List<WorkTypeBean> datas) {
        int width = (ScreenUtils.getRealWidth() - DpPxUtils.dp2px(64)) / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,DpPxUtils.dp2px(40));
        for (int i = 0; i < datas.size(); i++) {
            WorkTypeBean item = datas.get(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(item.getTitle());
            checkBox.setTextColor(ContextCompat.getColorStateList(this, R.color.text_blue_black_selecter));
            checkBox.setTextSize(14);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setBackgroundResource(R.drawable.grayrim_lightbluefill_selector);
            checkBox.setButtonDrawable(R.color.transparent);
            checkBox.setLayoutParams(layoutParams);
            db.layoutContent.addView(checkBox);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    submit(item.getId()+"");
//                    tags.add(item.getId()+"");
                } else {
//                    tags.remove(item.getId()+"");
                }
            });
        }

    }

}