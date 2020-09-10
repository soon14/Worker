package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/10
 * author: SmallCake
 */
public class WorkRecommendResponse implements Serializable {
    private int page;
    private int totalPage;
    private List<JobBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<JobBean> getItems() {
        return items;
    }

    public void setItems(List<JobBean> items) {
        this.items = items;
    }
}
