package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Date: 2020/8/28
 * author: SmallCake
 */
public class WorkerResponse implements MultiItemEntity, Serializable {
    public WorkerResponse(int itemType) {
        this.itemType = itemType;
    }

    int itemType;
    @Override
    public int getItemType() {
        return itemType;
    }
}
