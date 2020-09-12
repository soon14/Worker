package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class OrderResponse implements Serializable {


    /**
     * items : [{"address":"string","advanceType":0,"allEarned":0,"confirmedAt":"string","createdAt":"string","day":0,"endDate":"string","id":0,"isSafe":0,"listId":0,"num":0,"price":0,"settleAt":"string","settleType":0,"sn":"string","startDate":"string","status":0,"typeId":0,"typeTitle":"string","userId":0,"workDays":0}]
     * page : 0
     * totalPage : 0
     */

    private int page;
    private int totalPage;
    private List<OrderBean> items;

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

    public List<OrderBean> getItems() {
        return items;
    }

    public void setItems(List<OrderBean> items) {
        this.items = items;
    }
}
