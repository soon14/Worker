package com.xsd.jx.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.xsd.jx.R;
import com.xsd.jx.bean.AddrBean;
import com.xsd.jx.listener.OnAddrListener;
import com.xsd.utils.GsonUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public class BottomAddrPop extends BottomPopupView {
    private AddrBean selectCity;//市
    private AddrBean selectDistrict;//区

    private List<AddrBean> allAddrBeans;
    private TextView tvCity;
    private TextView tvDistrict;
    private OnAddrListener listener;

    @Override
    protected int getMaxHeight() {
        return (int) (super.getMaxHeight()*0.86f);
    }

    @Override
    protected int getPopupHeight() {
        return (int) (super.getPopupHeight()*0.86f);
    }

    public void setListener(OnAddrListener listener) {
        this.listener = listener;
    }

    public BottomAddrPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_addr;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_title).setOnClickListener(view -> dismiss());
        tvCity = findViewById(R.id.tv_city);
        tvDistrict = findViewById(R.id.tv_district);
        EditText etAddr = findViewById(R.id.et_addr);


        String jsonAddr = getJson(getContext(), "area.json");
        allAddrBeans = GsonUtils.jsonToList(jsonAddr, AddrBean.class);
        List<AddrBean> datas = new ArrayList<>();
        for (int i = 0; i < allAddrBeans.size(); i++) {
            AddrBean addrBean = allAddrBeans.get(i);
            //湖北下面的
            if (addrBean.getParent_id()==1681){
                if (selectCity==null){
                    selectCity=addrBean;
                    tvCity.setText(addrBean.getName());
                    List<AddrBean> datas3 = getAddrBeans(selectCity.getId());
                    selectDistrict = datas3.get(0);
                    if (selectDistrict!=null)tvDistrict.setText(selectDistrict.getName());
                }
                datas.add(addrBean);
            }
        }
        tvCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityPop(datas);
            }
        });
        tvDistrict.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectCity!=null){
                    L.e(selectCity.getId()+"===="+selectCity.getName());
                    List<AddrBean> datas3 = getAddrBeans(selectCity.getId());
                    L.e("datas3===="+datas3.size());
                    showDistrictPop(datas3);
                }
            }
        });

        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = etAddr.getText().toString();
                if (TextUtils.isEmpty(s)){
                    ToastUtil.showLong("请输入详情地址描述！");
                    return;
                }
                dismiss();
                if (listener!=null)listener.onAddrSelect(selectCity,selectDistrict,s);
            }
        });


    }

    private void showCityPop(List<AddrBean> datas) {
        AddrSelectPop addrSelectPop = new AddrSelectPop(getContext(), datas,"——  请选择市  ——");
        addrSelectPop.setListener(new AddrSelectPop.OnCityListener() {
            @Override
            public void onCityClick(AddrBean city) {
                addrSelectPop.dismiss();
                selectCity = city;
                int id = city.getId();
                tvCity.setText(city.getName());
                //查找出选中后的市对应的区县
                List<AddrBean> datas3 = getAddrBeans(id);
                //动态刷新,如果有区，就显示，并动态刷新，没有就隐藏
                if (datas3.size()>0){
                    selectDistrict = datas3.get(0);
                    tvDistrict.setText(selectDistrict.getName());
                    showDistrictPop(datas3);
                }else {
                    selectDistrict=null;
                    tvDistrict.setText("");
                }
            }
        });

        new XPopup.Builder(getContext())
                .atView(tvCity)
                .offsetY(8)
                .setPopupCallback(new SimpleCallback(){
                    @Override
                    public void onShow(BasePopupView popupView) {
                        super.onShow(popupView);
                        tvCity.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_blue));
                        tvCity.setBackgroundResource(R.drawable.round6_bluerim_bg);
                        tvCity.getPaint().setFakeBoldText(true);

                    }

                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        tvCity.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_black));
                        tvCity.setBackgroundResource(R.drawable.round6_grayrim_bg);
                        tvCity.getPaint().setFakeBoldText(false);

                    }
                })
                .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(addrSelectPop)
        .show();
    }

    @NonNull
    private List<AddrBean> getAddrBeans(int id) {
        List<AddrBean> datas3 = new ArrayList<>();
        for (int i = 0; i < allAddrBeans.size(); i++) {
            AddrBean addrBean = allAddrBeans.get(i);
            //湖北下面的
            if (addrBean.getParent_id()==id){
                datas3.add(addrBean);
            }
        }
        return datas3;
    }

    private void showDistrictPop(List<AddrBean> datas) {
        if (datas==null||datas.size()==0)return;
        AddrSelectPop addrSelectPop = new AddrSelectPop(getContext(), datas,"——  请选择区  ——");
        addrSelectPop.setListener(new AddrSelectPop.OnCityListener() {
            @Override
            public void onCityClick(AddrBean district) {
                tvDistrict.setText(district.getName());
                selectDistrict = district;
                addrSelectPop.dismiss();
            }
        });
        new XPopup.Builder(getContext())
                .atView(tvDistrict)
                .offsetY(8)
                .setPopupCallback(new SimpleCallback(){
                    @Override
                    public void onShow(BasePopupView popupView) {
                        super.onShow(popupView);
                        tvDistrict.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_blue));
                        tvDistrict.setBackgroundResource(R.drawable.round6_bluerim_bg);
                        tvDistrict.getPaint().setFakeBoldText(true);

                    }

                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        tvDistrict.setTextColor(ContextCompat.getColor(getContext(),R.color.tv_black));
                        tvDistrict.setBackgroundResource(R.drawable.round6_grayrim_bg);
                        tvDistrict.getPaint().setFakeBoldText(false);
                    }
                })
                .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(addrSelectPop)
                .show();
    }

    private void readAddrJson() {
        String jsonAddr = getJson(getContext(), "area.json");
        List<AddrBean> addrBeans = GsonUtils.jsonToList(jsonAddr, AddrBean.class);
        for (int i = 0; i < addrBeans.size(); i++) {
            AddrBean addrBean = addrBeans.get(i);

            //湖北下面的
            if (addrBean.getParent_id()==1681){
                L.e(addrBean.getName());

            }
        }
    }


    private String getJson(Context context, String fileName) {
        StringBuilder builder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return builder.toString();
    }

}
