package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.HelpUserResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/24
 * author: SmallCake
 */
public class HelpRegistAdapter extends BaseQuickAdapter<HelpUserResponse, BaseViewHolder> {
    public HelpRegistAdapter() {
        super(R.layout.item_help_regist);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HelpUserResponse helpUserResponse) {

    }
}
