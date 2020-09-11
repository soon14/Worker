package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/11
 * author: SmallCake
 */
public class InviteListResponse implements Serializable {
    private List<JobBean> items;
    private int count;

    public List<JobBean> getItems() {
        return items;
    }

    public void setItems(List<JobBean> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
