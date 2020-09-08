package com.xsd.jx.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.WagePayResponse;
import com.xsd.jx.databinding.ItemWagePayBinding;
import com.xsd.jx.utils.TextViewUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/9/1
 * author: SmallCake
 */
public class WagePayAdapter extends BaseQuickAdapter<WagePayResponse, BaseDataBindingHolder<ItemWagePayBinding>> {
    public WagePayAdapter() {
        super(R.layout.item_wage_pay);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemWagePayBinding> baseViewHolder, WagePayResponse wagePayResponse) {
        ItemWagePayBinding dataBinding = baseViewHolder.getDataBinding();
        int childCount = dataBinding.layoutWorkers.getChildCount();
        if (childCount>0){
            dataBinding.layoutWorkers.removeAllViews();
        }
        for (int i = 0; i < 3; i++) {
            View viewWorker = LayoutInflater.from(getContext()).inflate(R.layout.item_wagepay_worker, null);
            if (i>0){
                viewWorker.setVisibility(View.GONE);
            }
            dataBinding.layoutWorkers.addView(viewWorker);
        }
        dataBinding.layoutExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childCount = dataBinding.layoutWorkers.getChildCount();
                if (dataBinding.layoutWorkers.getChildAt(childCount-1).getVisibility()==View.GONE){
                    TextViewUtils.setRightIcon(R.mipmap.ic_gray_arrow_up_big,dataBinding.tvExpand);
                    for (int i = 1; i <childCount ; i++) {
                        dataBinding.layoutWorkers.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }else {
                    for (int i = 1; i <childCount ; i++) {
                        dataBinding.layoutWorkers.getChildAt(i).setVisibility(View.GONE);
                    }
                    TextViewUtils.setRightIcon(R.mipmap.ic_gray_arrow_down_big,dataBinding.tvExpand);
                }


            }
        });



    }
}
