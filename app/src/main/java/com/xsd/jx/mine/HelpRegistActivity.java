package com.xsd.jx.mine;

import android.os.Bundle;
import android.text.TextUtils;

import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BannerBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.custom.VerifyCountTimer;
import com.xsd.jx.databinding.ActivityHelpRegistBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

/**
 * 帮工友注册
 */
public class HelpRegistActivity extends BaseBindBarActivity<ActivityHelpRegistBinding> {
    private VerifyCountTimer mTimer;
    private int wtIds;//工种单选
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
                    //改为单选工种
                    PopShowUtils.showWorkTypeSelect(this, workTypeBean -> {
                        db.tvTypeWork.setText(workTypeBean.getTitle());
                        wtIds = workTypeBean.getId();
                    });
                    break;
                case R.id.tv_nation:
                    PopShowUtils.showNationList(this, nationName -> {
                        nation = nationName;
                        db.tvNation.setText(nationName);
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
        if (wtIds==0){
            ToastUtil.showLong("请选择工种！");
            return;
        }
        String mobile = db.etMobile.getText().toString();
        String code = db.etCode.getText().toString();
        String name = db.etName.getText().toString();
        String idCard = db.etIdcard.getText().toString();



        dataProvider.user.helpReg(mobile,code,name,idCard,wtIds+"",nation,workYears)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        Apollo.emit(EventStr.UPDATE_USER_INFO);
                        //清除所有的数据
                        clearAllData();
                    }

                    private void clearAllData() {
                        db.etMobile.setText("");
                        db.etCode.setText("");
                        db.etName.setText("");
                        db.etIdcard.setText("");
                        db.tvTypeWork.setText("");
                        nation = "汉族";
                        db.tvNation.setText(nation);
                        workYears = "1~5年";
                        db.tvWorkYears.setText(workYears);
                        mTimer.cancel();
                        mTimer.onFinish();
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