package com.xsd.jx.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;


/**
 * Date: 2020/3/25
 * author: SmallCake
 */
public class ProvinceAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProvinceAdapter() {
        super(R.layout.item_province);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
