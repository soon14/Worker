package com.xsd.jx.custom;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.xsd.jx.R;
import com.xsd.jx.adapter.AddrSelectAdapter;
import com.xsd.jx.bean.AddrBean;
import com.xsd.utils.ScreenUtils;

import java.util.List;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public class AddrSelectPop extends PartShadowPopupView {
    private OnCityListener listener;



    @Override
    protected int getPopupWidth() {
        return ScreenUtils.getRealWidth();
    }

    @Override
    public int getMinimumWidth() {
        return ScreenUtils.getRealWidth();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return ScreenUtils.getRealWidth();
    }

    interface OnCityListener  {
        void onCityClick(AddrBean city);
    }

    public void setListener(OnCityListener listener) {
        this.listener = listener;
    }

    private AddrSelectAdapter mAdapter =new AddrSelectAdapter();
    private List<AddrBean> datas;
    private String title;
    public AddrSelectPop(@NonNull Context context,List<AddrBean> datas,String title) {
        super(context);
        this.datas = datas;
        this.title=title;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_recycler_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getRealWidth(), LinearLayout.LayoutParams.WRAP_CONTENT));
        mAdapter.setList(datas);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AddrBean item = (AddrBean) adapter.getItem(position);
                if (listener!=null)listener.onCityClick(item);
            }
        });
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
//        tvTitle.setGravity(title.contains("市")?Gravity.LEFT:Gravity.RIGHT);

    }

    private void setDatas(List<AddrBean> datas){
        if (mAdapter!=null)mAdapter.setList(datas);
    }


}
