package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.AwardResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class AwardAdapter extends BaseQuickAdapter<AwardResponse, BaseViewHolder> {
    public AwardAdapter() {
        super(R.layout.item_award);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AwardResponse awardResponse) {

    }
}
