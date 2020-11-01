package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/8/28
 * author: SmallCake
 */
public class WorkerResponse implements Serializable {

    /**
     * page : 1
     * totalPage : 1
     * items : [{"userId":1,"name":"张三","birthday":"1975-05-18","sex":1,"avatar":"","isCertification":1,"workYears":"10-15","age":0},{"userId":1,"name":"李四","birthday":"1975-05-18","sex":1,"avatar":"","isCertification":1,"workYears":"10-15","age":0}]
     * workTypes : [{"id":1,"title":"工长"},{"id":2,"title":"电工"}]
     */

    private int page;
    private int totalPage;
    private List<WorkerBean> items;
    private List<WorkTypeBean> workTypes;

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

    public List<WorkerBean> getItems() {
        return items;
    }

    public void setItems(List<WorkerBean> items) {
        this.items = items;
    }

    public List<WorkTypeBean> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkTypeBean> workTypes) {
        this.workTypes = workTypes;
    }



}
