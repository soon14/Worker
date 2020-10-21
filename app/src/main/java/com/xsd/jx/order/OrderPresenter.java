package com.xsd.jx.order;

/**
 * Date: 2020/9/16
 * author: SmallCake
 * 控制器
 */
public class OrderPresenter {
    private OrderModel model;
    public OrderPresenter(OrderView view) {
         model = new OrderModel(view);
        model.initView();
    }
    public void loadData(){
        model.loadData();
    }
    public void setPage(){
        model.setPage(1);
    }
    public void getType(){
        model.getType();
    }

}
