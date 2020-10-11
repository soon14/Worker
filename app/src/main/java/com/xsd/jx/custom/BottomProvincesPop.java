package com.xsd.jx.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.adapter.AddrSelectAdapter;
import com.xsd.jx.bean.AddrBean;
import com.xsd.jx.listener.OnAddrListener;
import com.xsd.utils.GsonUtils;
import com.xsd.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/9/3
 * author: SmallCake
 * 从底部弹出的省市区选择器
 */
public class BottomProvincesPop extends BottomPopupView {
    private AddrBean selectCity;//市
    private AddrBean selectDistrict;//区

    private List<AddrBean> allAddrBeans;
    private TextView tvCity;
    private TextView tvDistrict;
    private OnAddrListener listener;
    private AddrSelectAdapter mAdapter =new AddrSelectAdapter();

    private View viewCity;
    private View viewDistrict;
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

    public BottomProvincesPop(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_provinces;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_title).setOnClickListener(view -> dismiss());
        tvCity = findViewById(R.id.tv_city);
        tvDistrict = findViewById(R.id.tv_district);
        viewCity = findViewById(R.id.view_city);
        viewDistrict = findViewById(R.id.view_district);
        LinearLayout layoutDistrict = findViewById(R.id.layout_district);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        recyclerView.setAdapter(mAdapter);

        String jsonAddr = getJson(getContext(), "area.json");
        allAddrBeans = GsonUtils.jsonToList(jsonAddr, AddrBean.class);
        List<AddrBean> datas = new ArrayList<>();//湖北下面所有的市
        for (int i = 0; i < allAddrBeans.size(); i++) {
            AddrBean addrBean = allAddrBeans.get(i);
            //湖北（1681）下面的市
            if (addrBean.getParent_id()==1681){
                if (selectCity==null){
                    selectCity=addrBean;
                    tvCity.setText(addrBean.getName());
                    List<AddrBean> datas3 = getAddrBeans(selectCity.getId());
                    selectDistrict = datas3.get(0);//默认选中该市对应的第一个区
                    if (selectDistrict!=null)tvDistrict.setText(selectDistrict.getName());
                }
                datas.add(addrBean);
            }
        }
        mAdapter.setList(datas);
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
                dismiss();
                if (listener!=null)listener.onAddrSelect(selectCity,selectDistrict);
            }
        });
        //市区选择事件
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AddrBean item = (AddrBean) adapter.getItem(position);
                if (item.getLevel()==2){//选择的是市，查询出对应的区,默认选中第一个，如果没有就隐藏区
                    selectCity=item;
                    tvCity.setText(item.getName());
                    List<AddrBean> addrBeans = getAddrBeans(item.getId());
                    if (addrBeans==null||addrBeans.size()==0){
                        layoutDistrict.setVisibility(INVISIBLE);
                        selectDistrict=null;
                        dismiss();
                        listener.onAddrSelect(selectCity,selectDistrict);
                    }else {
                        layoutDistrict.setVisibility(VISIBLE);
                        selectDistrict = addrBeans.get(0);//默认选中该市对应的第一个区
                        tvDistrict.setText(selectDistrict.getName());
                        showDistrictPop(addrBeans);
                    }


                }else if (item.getLevel()==3){
                    selectDistrict=item;
                    tvDistrict.setText(item.getName());
                    dismiss();
                    listener.onAddrSelect(selectCity,selectDistrict);
                }

            }
        });


    }

    private void showCityPop(List<AddrBean> datas) {
        tvCity.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_blue));
        viewCity.setVisibility(VISIBLE);
        tvDistrict.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewDistrict.setVisibility(INVISIBLE);
        mAdapter.setList(datas);
    }
    private void showDistrictPop(List<AddrBean> datas) {
        tvCity.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewCity.setVisibility(INVISIBLE);
        tvDistrict.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_blue));
        viewDistrict.setVisibility(VISIBLE);
        mAdapter.setList(datas);
    }

    /**
     * 查询当前市下面的所有区
     * @param id  市的id
     * @return 根据市id查询的所有区集合
     */
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


    //读取assets下的json资源，转换为字符串
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
