package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.ItemGetinfoApplyWorkersBinding;
import com.xsd.jx.databinding.ItemGetinfoFullBinding;
import com.xsd.jx.databinding.ItemGetinfoWaitcommentBinding;
import com.xsd.jx.databinding.ItemGetinfoWaitpayBinding;

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
        addItemType(1, R.layout.item_getinfo_apply_workers);

        addItemType(2, R.layout.item_getinfo_full);

        addItemType(3, R.layout.item_getinfo_waitpay);//工期中
        addItemType(4, R.layout.item_getinfo_waitpay);//待结算
        addItemType(5, R.layout.item_getinfo_waitcomment);//待评价

        addItemType(6, R.layout.item_getinfo_full);
        addItemType(7, R.layout.item_getinfo_full);
        addItemType(8, R.layout.item_getinfo_full);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, WorkerBean item) {
        // 根据返回的 type 分别设置数据
        switch (item.getItemType()) {
            case 1:
                ItemGetinfoApplyWorkersBinding dataBinding1 = (ItemGetinfoApplyWorkersBinding) helper.getDataBinding();
                dataBinding1.setItem(item);
                break;
            case 2:
            case 6:
            case 7:
            case 8:
                ItemGetinfoFullBinding dataBinding2 = (ItemGetinfoFullBinding) helper.getDataBinding();
                dataBinding2.setItem(item);
                break;

            case 3:
            case 4:
                ItemGetinfoWaitpayBinding dataBinding4 = (ItemGetinfoWaitpayBinding) helper.getDataBinding();
                dataBinding4.setItem(item);
                dataBinding4.tvPriceDay.setText("工价"+price+"元/天  考勤"+item.getCheckDay()+"天");
                break;
            case 5:
                ItemGetinfoWaitcommentBinding dataBinding5 = (ItemGetinfoWaitcommentBinding) helper.getDataBinding();
                dataBinding5.setItem(item);
                break;
        }
    }
}
