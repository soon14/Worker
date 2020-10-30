package com.xsd.jx.pop;

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
import java.util.Arrays;
import java.util.List;

/**
 * Date: 2020/9/3
 * author: SmallCake
 * 三级选择器
 * 从底部弹出的省市区选择器
 */
public class BottomProvincesPop extends BottomPopupView {

    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvDistrict;
    private View viewProvince;
    private View viewCity;
    private View viewDistrict;

    private AddrBean selectProvince;//省
    private AddrBean selectCity;//市
    private AddrBean selectDistrict;//区

    private List<AddrBean> allAddrBeans;

    private OnAddrListener listener;
    private AddrSelectAdapter mAdapter =new AddrSelectAdapter();


    private List<String> openProvinces = new ArrayList<>();
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
        openProvinces.add("湖北省");
        openProvinces.add("重庆市");

        tvProvince = findViewById(R.id.tv_province);
        tvCity = findViewById(R.id.tv_city);
        tvDistrict = findViewById(R.id.tv_district);

        viewProvince = findViewById(R.id.view_province);
        viewCity = findViewById(R.id.view_city);
        viewDistrict = findViewById(R.id.view_district);
        LinearLayout layoutDistrict = findViewById(R.id.layout_district);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        recyclerView.setAdapter(mAdapter);

        String jsonAddr = getJson(getContext(), "area.json");
        allAddrBeans = GsonUtils.jsonToList(jsonAddr, AddrBean.class);

        //从所有的数据中找出已经开放的省份数据
        List<AddrBean> provinceDatas = new ArrayList<>();//湖北下面所有的市
        for (int i = 0; i < allAddrBeans.size(); i++) {
            AddrBean addrBean = allAddrBeans.get(i);
            if (isHave(addrBean.getName())){
                provinceDatas.add(addrBean);
            }
            if (openProvinces.size()==0)break;
        }
        selectProvince = provinceDatas.get(0);
        mAdapter.setList(provinceDatas);
        tvProvince.setOnClickListener(view -> {
            tvCity.setVisibility(INVISIBLE);
            tvDistrict.setVisibility(INVISIBLE);
            showProvinceData(provinceDatas);
        });
        tvCity.setOnClickListener(view -> {
            if (selectProvince!=null){
                tvDistrict.setVisibility(INVISIBLE);
                List<AddrBean> datas2 = getAddrBeans(selectProvince.getId());
                showCityData(datas2);
            }
        });
        tvDistrict.setOnClickListener(view -> {
            if (selectCity!=null){
                List<AddrBean> datas3 = getAddrBeans(selectCity.getId());
                showDistrictData(datas3);
            }
        });

        //市区选择事件
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            AddrBean item = (AddrBean) adapter.getItem(position);
            int level = item.getLevel();
            switch (level){
                case 1:
                    selectProvince = item;
                    tvProvince.setText(item.getName());
                    //根据选中的省查出对应的市
                    List<AddrBean> addrCitys = getAddrBeans(item.getId());
                    selectCity = addrCitys.get(0);//默认选中该市对应的第一个区
                    tvCity.setText(selectCity.getName());
                    showCityData(addrCitys);
                    break;
                case 2://选择的是市，查询出对应的区,默认选中第一个，如果没有就隐藏区
                    selectCity=item;
                    tvCity.setText(item.getName());
                    //根据选中的市查出对应的区，没有就直接关闭弹框
                    List<AddrBean> addrBeans = getAddrBeans(item.getId());
                    if (addrBeans.size()==0){
                        layoutDistrict.setVisibility(INVISIBLE);
                        selectDistrict=null;
                        dismiss();
                        listener.onAddrSelect(selectProvince,selectCity,selectDistrict);
                    }else {
                        layoutDistrict.setVisibility(VISIBLE);
                        selectDistrict = addrBeans.get(0);//默认选中该市对应的第一个区
                        tvDistrict.setText(selectDistrict.getName());
                        showDistrictData(addrBeans);
                    }
                    break;
                case 3:
                    selectDistrict=item;
                    tvDistrict.setText(item.getName());
                    dismiss();
                    listener.onAddrSelect(selectProvince,selectCity,selectDistrict);
                    break;
            }


        });


    }
    private boolean isHave(String name){
        for (int i = 0; i < openProvinces.size(); i++) {
            String openProvince = openProvinces.get(i);
            if (name.equals(openProvince)){
                openProvinces.remove(openProvince);
                return true;
            }
        }
        return false;
    }

    //显示省的信息
    private void showProvinceData(List<AddrBean> datas) {

        tvProvince.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_blue));
        viewProvince.setVisibility(VISIBLE);
        tvCity.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewCity.setVisibility(INVISIBLE);
        tvDistrict.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewDistrict.setVisibility(INVISIBLE);
        mAdapter.setList(datas);
    }
    //显示市的信息
    private void showCityData(List<AddrBean> datas) {
        tvCity.setVisibility(VISIBLE);
        tvDistrict.setVisibility(VISIBLE);
        tvProvince.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewProvince.setVisibility(INVISIBLE);
        tvCity.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_blue));
        viewCity.setVisibility(VISIBLE);
        tvDistrict.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewDistrict.setVisibility(INVISIBLE);
        mAdapter.setList(datas);
    }
    //显示区的信息
    private void showDistrictData(List<AddrBean> datas) {
        tvDistrict.setVisibility(VISIBLE);
        tvProvince.setTextColor(ContextCompat.getColor(this.getContext(),R.color.tv_gray));
        viewProvince.setVisibility(INVISIBLE);
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
