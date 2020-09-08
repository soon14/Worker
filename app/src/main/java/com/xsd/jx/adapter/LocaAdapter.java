package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.LocaResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/27
 * author: SmallCake
 */
public class LocaAdapter extends BaseQuickAdapter<LocaResponse, BaseViewHolder> {
    public LocaAdapter() {
        super(R.layout.item_loca);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LocaResponse locaResponse) {

    }
}
