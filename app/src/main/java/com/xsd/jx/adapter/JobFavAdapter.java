package com.xsd.jx.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.databinding.ItemJobFavBinding;

import org.jetbrains.annotations.NotNull;

/**
 * Date: 2020/8/18
 * author: SmallCake
 *
 * 我的-收藏的工作 status 状态 1:可报名 2:已过期
 *
 */
public class JobFavAdapter extends BaseQuickAdapter<JobBean, BaseDataBindingHolder<ItemJobFavBinding>> implements LoadMoreModule {

    public JobFavAdapter() {
        super(R.layout.item_job_fav);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemJobFavBinding> helper, JobBean item) {
        ItemJobFavBinding db = helper.getDataBinding();
        db.setItem(item);
    }




}
