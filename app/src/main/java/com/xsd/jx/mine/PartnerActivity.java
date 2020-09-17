package com.xsd.jx.mine;

import android.os.Bundle;

import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityPartnerBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

/**
 * 城市合伙人意向提交
 *  idType  身份 1:建筑企业 2:劳务公司 3:个人
 *  purpose 合作意向 1:项目入股 2:资源合作 3:委托招工
 */
public class PartnerActivity extends BaseBindBarActivity<ActivityPartnerBinding> {
    private int idType,purpose;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_partner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void initView() {
        tvTitle.setText("城市合伙人");
        DataBindingAdapter.bindImageRoundUrl(db.ivTop,R.mipmap.bg_partner_top,6);
    }

    private void onEvent() {
        db.setClicklistener(view -> {
            switch (view.getId()){
                case R.id.tv_id_type:
                    PopShowUtils.showIdTypes(db.tvIdType, (position, text) -> {
                        idType = position+1;
                        db.tvIdType.setText(text);
                    });
                    break;
                case R.id.tv_purpose:
                    PopShowUtils.showPurpose(db.tvPurpose, (position, text) -> {
                        purpose = position+1;
                        db.tvPurpose.setText(text);
                    });
                    break;
                case R.id.tv_confirm:
                    submit();
                    break;
            }
        });
    }

    private void submit() {
        if (EditTextUtils.isEmpty(db.etName,db.etMobile))return;
        if (idType==0){
            ToastUtil.showLong("请选择您的身份");
            return;
        }
        if (purpose==0){
            ToastUtil.showLong("请选择合作意向");
            return;
        }
        String name = db.etName.getText().toString();
        String mobile = db.etMobile.getText().toString();
        dataProvider.user.cityPartner(name,mobile,idType,purpose)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                    }
                });
    }
}