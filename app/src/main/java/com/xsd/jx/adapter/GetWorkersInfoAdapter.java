package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.ItemGetWorkersInfo1Binding;
import com.xsd.jx.databinding.ItemGetWorkersInfo2Binding;
import com.xsd.jx.databinding.ItemGetWorkersInfo3Binding;
import com.xsd.jx.databinding.ItemGetWorkersInfo5Binding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/28
 * author: SmallCake
 * 招工详情页，工人信息列表适配器
 * type 状态 -1:不展示(有预付款项未付不显示给用户) )
 *  1:正在招
 *  2:已招满/待开工(所有用户已确认)
 *  3:工期中 (和待结算一个状态)
 *  4:待结算
 *  5:待评价
 *  6:已完成
 *  7:已取消
 *  8:已过期
 *  status:状态 1:未处理 2：已确认 3：已拒绝
 */
public class GetWorkersInfoAdapter extends BaseMultiItemQuickAdapter<WorkerBean, BaseDataBindingHolder> {
    String price;
    public GetWorkersInfoAdapter(String price) {
        super();
        this.price = price;
        // 绑定 layout 对应的 type
        addItemType(1, R.layout.item_get_workers_info_1);//正在招
        addItemType(2, R.layout.item_get_workers_info_2);//待开工
        addItemType(3, R.layout.item_get_workers_info_3);//工期中
        addItemType(4, R.layout.item_get_workers_info_3);//待结算
        addItemType(5, R.layout.item_get_workers_info_5);//待评价
        addItemType(6, R.layout.item_get_workers_info_2);
        addItemType(7, R.layout.item_get_workers_info_2);
        addItemType(8, R.layout.item_get_workers_info_2);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, WorkerBean item) {
        // 根据返回的 type 分别设置数据
        switch (item.getItemType()) {
            case 1:
                ((ItemGetWorkersInfo1Binding) helper.getDataBinding()).setItem(item);
                break;
            case 2:
            case 6:
            case 7:
            case 8:
                ((ItemGetWorkersInfo2Binding) helper.getDataBinding()).setItem(item);
                break;
            case 3:
            case 4:
                ItemGetWorkersInfo3Binding dataBinding3 = (ItemGetWorkersInfo3Binding) helper.getDataBinding();
                dataBinding3.setItem(item);
                //TODO 人数: 5人  天数: 1天
                dataBinding3.tvPriceDay.setText("人数: "+item.getEmployNum()+"人  天数: "+item.getCheckDay()+"天");
                break;
            case 5:
                ((ItemGetWorkersInfo5Binding) helper.getDataBinding()).setItem(item);
                break;
        }
    }
}
