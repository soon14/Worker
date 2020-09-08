package com.xsd.jx.manager;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.AddrBean;
import com.xsd.jx.custom.BottomAddrPop;
import com.xsd.jx.databinding.ActivityPushGetWorkersBinding;
import com.xsd.jx.listener.OnAddrListener;
import com.xsd.utils.SoftInputUtils;

/**
 * 发布招工
 */
public class PushGetWorkersActivity extends BaseBindBarActivity<ActivityPushGetWorkersBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_get_workers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                db.tvNum.setText(length+"/200");
            }
        });
        db.setClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.layout_addr:
                        showBottomAddr();
                        break;
                }
            }
        });

    }

    private void initView() {
        tvTitle.setText("发布招工");
        db.rbDayPay.setChecked(true);
        db.rbBuySafe.setChecked(true);
        db.rbPay2cost.setChecked(true);
    }
    BottomAddrPop bottomAddrPop;
    private void showBottomAddr() {
       if (bottomAddrPop==null){
           bottomAddrPop = new BottomAddrPop(this);
           bottomAddrPop.setListener(new OnAddrListener() {
               @Override
               public void onAddrSelect(AddrBean city, AddrBean district, String desc) {
                   db.tvAddr.setText("湖北省"+city.getName()+(district==null?"":district.getName())+(TextUtils.isEmpty(desc)?"":desc));
                   new Handler().postDelayed(() -> SoftInputUtils.closeSoftInput(PushGetWorkersActivity.this),800);
               }
           });
           new XPopup.Builder(this)
                   .setPopupCallback(new SimpleCallback(){
                       @Override
                       public void onDismiss(BasePopupView popupView) {
                           super.onDismiss(popupView);
                           new Handler().postDelayed(() -> SoftInputUtils.closeSoftInput(PushGetWorkersActivity.this),800);
                       }
                   })
                   .asCustom(bottomAddrPop)
                   .show();
       }else bottomAddrPop.show();

    }


}