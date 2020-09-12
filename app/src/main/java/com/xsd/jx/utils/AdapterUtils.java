package com.xsd.jx.utils;

import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xsd.jx.R;
import com.xsd.utils.SmallUtils;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class AdapterUtils {
    public static void setEmptyDataView(BaseQuickAdapter adapter){
        adapter.setEmptyView(LayoutInflater.from(SmallUtils.getApp().getApplicationContext()).inflate(R.layout.empty_view_nodata,null));
    }
}
