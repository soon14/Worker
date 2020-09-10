package com.xsd.jx.utils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobBean;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.SmallUtils;

/**
 * Date: 2020/1/15
 * author: SmallCake
 * 使用：直接在layout包裹的xml中的ImageView控件中url = "@{item.img_path}"
 */
public class DataBindingAdapter {

    //普通网络图片
    @BindingAdapter("url")
    public static void bindUrl(ImageView view, String imageUrl){
        Glide.with(view)
                .load(imageUrl)
                .into(view);
    }
    //圆形图片
    @BindingAdapter("circleUrl")
    public static void bindImageCircleUrl(ImageView view, Object imageUrl){
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_site)
                .error(R.mipmap.ic_site)
                .circleCrop();
        Glide.with(view)
                .load(imageUrl)
                .apply(options)
                .into(view);
    }
    //圆角图片,圆角系数,默认为9
    @BindingAdapter(value = {"roundUrl","roundRadius"}, requireAll = false)
    public static void bindImageRoundUrl(ImageView view, Object imageRoundUrl,int roundingRadius){
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_site)
                .error(R.mipmap.ic_site)
                .transform(new CenterCrop(),new RoundedCorners((int) DpPxUtils.dp2pxFloat(roundingRadius==0?9:roundingRadius)));
        Glide.with(view)
                .load(imageRoundUrl)
                .apply(options)
                .into(view);
    }
    /**
     * 上部为圆角的图片样式，
     * @param view ImageView
     * @param imageRoundUrl  要加载的图片url
     * @param roundingRadius 圆角弧度 默认9
     */
    @BindingAdapter(value = {"topRoundUrl","roundRadius"}, requireAll = false)
    public static void bindImageTopRoundUrl(ImageView view, String imageRoundUrl, float roundingRadius){
        CornerTransform transformation = new CornerTransform(SmallUtils.getApp(), DpPxUtils.dp2pxFloat(roundingRadius==0?9:roundingRadius));
        transformation.setExceptCorner(true, true, false, false);
        Glide.with(SmallUtils.getApp()).load(imageRoundUrl).apply(new RequestOptions().transform(transformation)).into(view);
    }
    //付款多少
    @BindingAdapter("advanceType")
    public static void advanceType(TextView tv, int type){
        switch (type){
            case 1:
                tv.setText("两成");
                break;
            case 2:
                tv.setText("全款");
                break;
            case 3:
                tv.setText("未预付");
                break;
        }
    }
    //工期：2020-07-29至2020-08-29(共30天)
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, JobBean item){
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    //工种类型：木工（共需3人）
    @BindingAdapter("workType")
    public static void workType(TextView tv, JobBean item){
        tv.setText(item.getTypeTitle()+"（共需"+item.getNum()+"人）");
    }

    @BindingAdapter("workDayInfo")
    public static void workDayInfo(TextView tv, JobBean item){
        tv.setText(item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("price")
    public static void price(TextView tv, String price){
        tv.setText(price+"元/天");
    }

    @BindingAdapter("isJoin")
    public static void isJoin(TextView tv, boolean isJoin){
        tv.setText(isJoin?"已报名":"报名上工");
        tv.setBackgroundResource(isJoin?R.drawable.round6_gray_bg:R.drawable.round6_blue_bg);
    }
}
