package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.InviteResponse;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/25
 * author: SmallCake
 */
public class InviteAdapter extends BaseQuickAdapter<InviteResponse, BaseViewHolder> {
    public InviteAdapter() {
        super(R.layout.item_invite);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InviteResponse inviteResponse) {

    }
}
