package com.xsd.jx.mine;

import android.os.Bundle;
import android.text.TextUtils;

import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BannerBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkTypeBean;
import com.xsd.jx.custom.BottomAddWorkTypePop;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.jx.databinding.ActivityHelpRegistBinding;
import com.xsd.jx.listener.OnNationSelectListener;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

import java.util.Set;

/**
 * 帮工友注册
 */
public class HelpRegistActivity extends BaseBindBarActivity<ActivityHelpRegistBinding> {
    private VerifyCountTimer mTimer;
    private Set<WorkTypeBean> workTypes;//工种
    private String nation = "汉族";
    private String workYears = "1~5年";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_regist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        getBanner();
    }
    private void getBanner() {
        dataProvider.site.banner(4)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<BannerBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<BannerBean> baseResponse) {
                        BannerBean data = baseResponse.getData();
                        if (data!=null&& !TextUtils.isEmpty(data.getContentPath()))
                            DataBindingAdapter.bindImageRoundUrl(db.ivTop,data.getContentPath(),6);
                    }
                });
    }
    private void initView() {
        tvTitle.setText("帮工友注册");
        tvRight.setText("帮注册记录");
        mTimer = new VerifyCountTimer(db.tvGetCode);
        DataBindingAdapter.bindImageRoundUrl(db.ivTop,R.mipmap.bg_partner_top,6);
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.tv_get_code:
                    getCode();
                    break;
                case R.id.tv_type_work:
                    new XPopup.Builder(this)
                            .asCustom(new BottomAddWorkTypePop(this, null, new BottomAddWorkTypePop.OnAddWorkTypesListener() {
                                @Override
                                public void addWorkTypes(Set<WorkTypeBean> types) {
                                    workTypes = types;
                                    StringBuilder builder = new StringBuilder();
                                    for (WorkTypeBean workType : workTypes) {
                                        builder.append(workType.getTitle()).append(",");
                                    }
                                    String s = builder.toString();
                                    s = s.substring(0,s.lastIndexOf(","));
                                    db.tvTypeWork.setText(s);
                                }
                            },false))
                            .show();
                    break;
                case R.id.tv_nation:
                    PopShowUtils.showNationList(this, new OnNationSelectListener() {
                        @Override
                        public void onSelect(String nationName) {
                            nation = nationName;
                            db.tvNation.setText(nationName);
                        }
                    });
                    break;
                case R.id.tv_work_years:
                    PopShowUtils.showWorkExp(v, new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            workYears = text;
                            db.tvWorkYears.setText(text);
                        }
                    });
                    break;
                case R.id.tv_confirm:
                    helpReg();
                    break;

            }
        });
        tvRight.setOnClickListener(v -> goActivity(HelpRegistListActivity.class));

    }
    private void helpReg() {
        if (EditTextUtils.isEmpty(db.etMobile,db.etCode,db.etName,db.etIdcard))return;
        if (workTypes==null||workTypes.size()==0){
            ToastUtil.showLong("请选择工种！");
            return;
        }
        String mobile = db.etMobile.getText().toString();
        String code = db.etCode.getText().toString();
        String name = db.etName.getText().toString();
        String idCard = db.etIdcard.getText().toString();

        StringBuilder builder = new StringBuilder();
        for (WorkTypeBean workType : workTypes) {
            builder.append(workType.getId()).append(",");
        }
        String wtIds = builder.toString();
        wtIds = wtIds.substring(0,wtIds.lastIndexOf(","));

        dataProvider.user.helpReg(mobile,code,name,idCard,wtIds,nation,workYears)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.UPDATE_USER_INFO);
                    }
                });
    }

    private void getCode() {
        if (EditTextUtils.isEmpty(db.etMobile))return;
        String s = db.etMobile.getText().toString();
        dataProvider.site.sendSms(s,"help")
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        mTimer.start();
                    }
                });
    }


}