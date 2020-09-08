package com.xsd.jx.adapter;

import com.xsd.jx.bean.AddrBean;
import com.xsd.utils.adapter.CakeBaseAdapter;

import org.json.JSONException;

import java.util.List;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public class AddrAdapter extends CakeBaseAdapter<AddrBean> {

    public AddrAdapter(List<AddrBean> listDatas, int layoutId) {
        super(listDatas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, AddrBean addrBean) throws JSONException {

    }
}
