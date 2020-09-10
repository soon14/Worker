package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ItemJobBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/18
 * author: SmallCake
 */
public class JobAdapter extends BaseQuickAdapter<JobBean, BaseDataBindingHolder<ItemJobBinding>> implements LoadMoreModule {

    public JobAdapter() {
        super(R.layout.item_job);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemJobBinding> helper, JobBean jobBean) {
        ItemJobBinding dataBinding = helper.getDataBinding();
        dataBinding.setItem(jobBean);
//        SpannableStringBuilder spanStr = SpannableStringUtils.getBuilder("上工地点：武汉市青山区建设一路31号武汉宝业中心A座\n")
//                .append("  位置")
//                .setResourceId(R.mipmap.ic_gray_loca).append("距您1.0KM").create();
//            dataBinding.tvAddr.setText(spanStr);
        helper.setText(R.id.tv_price,jobBean.getPrice()+"元/天");

    }



}
