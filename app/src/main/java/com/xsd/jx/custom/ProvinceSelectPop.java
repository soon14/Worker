package com.xsd.jx.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.jx.adapter.AddrSelectAdapter;
import com.xsd.jx.bean.AddrBean;
import com.xsd.utils.GsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/9/9
 * author: SmallCake
 * 查工价地区选择
 * 默认市湖北省
 * 查此下的市区id对应的价格
 */
public class ProvinceSelectPop extends PartShadowPopupView {
    OnCityListener listener;

    private AddrSelectAdapter mAdapter =new AddrSelectAdapter();
    private List<AddrBean> allAddrBeans;
    private AddrBean selectCity;//市


    public interface OnCityListener {
        void onCitySelect(AddrBean selectCity);
    }

    public void setListener(OnCityListener listener) {
        this.listener = listener;
    }

    public ProvinceSelectPop(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_province_selector;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvCity = findViewById(R.id.tv_city);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(mAdapter);


        String jsonAddr = getJson(getContext(), "area.json");
        allAddrBeans = GsonUtils.jsonToList(jsonAddr, AddrBean.class);
        List<AddrBean> datas = new ArrayList<>();
        for (int i = 0; i < allAddrBeans.size(); i++) {
            AddrBean addrBean = allAddrBeans.get(i);
            //湖北下面的
            if (addrBean.getParent_id()==1681){
                if (selectCity==null) selectCity=addrBean;
                datas.add(addrBean);
            }
        }

        mAdapter.setList(datas);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            AddrBean item = (AddrBean) adapter.getItem(position);
            selectCity=item;
            if (listener!=null)listener.onCitySelect(selectCity);
            dismiss();
        });
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
