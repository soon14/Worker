package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.AddrBean;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/9/3
 * author: SmallCake
 */
public class AddrSelectAdapter extends BaseQuickAdapter<AddrBean, BaseViewHolder> {
    public AddrSelectAdapter() {
        super(R.layout.item_addr);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AddrBean addrBean) {
        baseViewHolder.setText(R.id.tv_name,addrBean.getName());
    }
}
