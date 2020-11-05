package com.xsd.jx.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.math.MathUtils;
import com.xsd.jx.R;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.JobListResponse;
import com.xsd.jx.bean.MyGetWorkersResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.ToSettleResponse;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.bean.WorkerInfoResponse;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.RandomUtils;
import com.xsd.utils.SmallUtils;
import com.xsd.utils.SpannableStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Date: 2020/1/15
 * author: SmallCake
 */
public class DataBindingAdapter {

    //普通网络图片
    @BindingAdapter("url")
    public static void bindUrl(ImageView view, String imageUrl){
        Glide.with(view)
                .load(imageUrl)
                .into(view);
    }

    //圆角图片,圆角系数,默认为9
    @BindingAdapter("roundNormalUrl")
    public static void roundNormalUrl(ImageView view, Object imageRoundUrl){
        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop(),new RoundedCorners((int) DpPxUtils.dp2pxFloat(9)));
        Glide.with(view)
                .load(imageRoundUrl)
                .apply(options)
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
    @BindingAdapter("avatar")
    public static void avatar(ImageView view, Object imageUrl){
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_worker_head)
                .error(R.mipmap.ic_worker_head)
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
    //付款多少成
    @BindingAdapter("advanceType")
    public static void advanceType(TextView tv, int type){
        switch (type){
            case 1:
                tv.setText("已付2成");
                break;
            case 2:
                tv.setText("已付全款");
                break;
            case 3:
                tv.setText("未预付");
                tv.setVisibility(View.GONE);
                break;
        }
    }
    @BindingAdapter("advanceAmount")
    public static void advanceAmount(TextView tv, String amount){
        if (TextUtils.isEmpty(amount)||amount.equals("0")) tv.setVisibility(View.GONE);
        tv.setText("已付"+amount+"元");
    }
    //工期：2020-07-29至2020-08-29(共30天)
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, WorkerBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, JobBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, OrderBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, JobListResponse.ItemsBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, MyGetWorkersResponse.ItemsBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDay")
    public static void workDay(TextView tv, ToSettleResponse.ItemsBean item){
        if (item==null)return;
        tv.setText("工期："+item.getStartDate()+"至"+item.getEndDate());
    }
    //工种类型：木工（共需3人）
    @BindingAdapter("workType")
    public static void workType(TextView tv, JobBean item){
        if (item==null)return;
        tv.setText(item.getTypeTitle()+"（共需"+item.getNum()+"人）");
    }


    @BindingAdapter("workDayInfo")
    public static void workDayInfo(TextView tv, JobBean item){
        if (item==null)return;
        tv.setText(item.getStartDate()+"至"+item.getEndDate()+"(共"+item.getDay()+"天)");
    }
    @BindingAdapter("workDayInfo")
    public static void workDayInfo(TextView tv, MyGetWorkersResponse.ItemsBean item){
        if (item==null)return;
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
        tv.setClickable(!isJoin);
    }
    @BindingAdapter("isFav")
    public static void isFav(TextView tv, boolean isFav){
        tv.setText(isFav?"已收藏":"收藏");
        tv.setTextColor(ContextCompat.getColor(tv.getContext(),isFav?R.color.colorAccent:R.color.tv_black));
        TextViewUtils.setTopIcon(isFav?R.mipmap.ic_collected:R.mipmap.ic_uncollected,tv);
        AnimUtils.tabSelect(tv);
    }

    @BindingAdapter("userDesc")
    public static void userDesc(TextView tv, UserInfo info){
        if (info==null)return;
        int sex = info.getSex();// 1男 2女 3未知
        String sexStr="男";
        switch (sex){
            case 1: sexStr="男";break;
            case 2:sexStr="女";
                break;
            case 3:sexStr="未知";
                break;
        }
        String workYears = info.getWorkYears();
        String nation = info.getNation();
        StringBuilder builder = new StringBuilder(sexStr);
        if (!TextUtils.isEmpty(nation))builder.append(" · ").append(nation);
        if (!TextUtils.isEmpty(workYears)) builder.append(" · ").append(workYears);
        tv.setText(builder.toString());
    }
    //工人简约描述：
    @BindingAdapter("workerDesc")
    public static void workerDesc(TextView tv, WorkerInfoResponse info){
        if (info==null)return;
        int sex = info.getSex();// 1男 2女 3未知
        String sexStr="男";
        switch (sex){
            case 1: sexStr="男";break;
            case 2:sexStr="女";
                break;
            case 3:sexStr="未知";
                break;
        }
        String workYears = info.getWorkYears();

        StringBuilder builder = new StringBuilder();
        builder.append(sexStr).append(" · ")
                .append(info.getNation());
        if (!TextUtils.isEmpty(workYears))
            builder.append(" · ").append(workYears);
        tv.setText(builder.toString());
    }
    //招工人：45岁 · 男 · 5~10年经验 · 已实名
    @BindingAdapter("workerListDesc")
    public static void workerListDesc(TextView tv, WorkerBean info){
        if (info==null)return;
        int sex = info.getSex();// 1男 2女 3未知
        String sexStr="男";
        switch (sex){
            case 1: sexStr="男";break;
            case 2:sexStr="女";
                break;
            case 3:sexStr="未知";
                break;
        }
        String workYears = info.getWorkYears();
        boolean isCertification = info.getIsCertification()==1;

        StringBuilder builder = new StringBuilder();
        builder.append(info.getAge()).append("岁")
                .append(" · ").append(sexStr);
        if (!TextUtils.isEmpty(workYears))
            builder.append(" · ").append(workYears);
        if (isCertification)builder.append(" · ").append("已实名");
        tv.setText(builder.toString());
    }


    @BindingAdapter("sex")// 1男 2女 3未知
    public static void sex(TextView tv, int sex){
        String sexStr="男";
        switch (sex){
            case 1: sexStr="男";break;
            case 2:sexStr="女";
                break;
            case 3:sexStr="未知";
                break;
        }
        tv.setText(sexStr);

    }
    @BindingAdapter("intro")
    public static void isJoin(TextView tv, String intro){
        tv.setText(TextUtils.isEmpty(intro)?"我是一个踏实肯干的人，各位老板选我准没错":intro);
    }
    /**
     *  我的招工适配器，底部状态信息{@link R.layout.item_mygetworkers_geting}
     */
    @BindingAdapter("layoutWorkers")
    public static void layoutWorkers(LinearLayout layout, int tobeConfirmNum){
        TextView tv0 = (TextView) layout.getChildAt(0);//左侧描述
        TextView tv1 = (TextView) layout.getChildAt(1);//右侧按钮
        if (tobeConfirmNum>0){
//            tv0.setText("有"+tobeConfirmNum+"位报名待确认工人");
            tv0.setText("有待确认的工人");
            tv1.setText("查看工人");
        }else{
            tv0.setText("还没有工人报名，您可以主动招工人");
            tv1.setText("主动招人");
        }
    }

    /**
     * 结款情况：2成/1200元
     */
    @BindingAdapter("payedStatus")
    public static void payedStatus(TextView tv, MyGetWorkersResponse.ItemsBean itemsBean){
        if (itemsBean==null)return;
        int advanceType = itemsBean.getAdvanceType();//结算方式 预付款类型 1:两成 2:全款 3:不预付
        int price = Integer.parseInt(itemsBean.getPrice());
        int num = itemsBean.getNum();
        int isPayedPrice=0;

        String at = "不预付";
        switch (advanceType){
            case 1:
                at="2成";
                isPayedPrice = (int) (price*num*0.2);
            break;
            case 2:at="全款";
                isPayedPrice=price*num;
            break;
            case 3:at="不预付";break;
        }
        tv.setText(at+"/"+isPayedPrice+"元");
    }
    //是否邀请上工
    @BindingAdapter("isInvite")
    public static void isInvite(TextView tv, boolean isInvite){
        tv.setText(isInvite?"已邀请":"邀请上工");
        tv.setClickable(!isInvite);
        tv.setEnabled(!isInvite);
        tv.setBackgroundResource(isInvite?R.drawable.round6_gray_bg:R.drawable.round6_blue_bg);
    }
    //需要人数：已招0人/共需20人
    @BindingAdapter("needNum")
    public static void needNum(TextView tv, JobBean item){
        int joinedNum = item.getConfirmedNum();
        int num = item.getNum();
        tv.setText("需要人数：已招"+joinedNum+"人/共需"+num+"人");
    }
    @BindingAdapter("needNum")
    public static void needNum(TextView tv, MyGetWorkersResponse.ItemsBean item){
        int confirmedNum = item.getConfirmedNum();
        int num = item.getNum();
        tv.setText("需要人数：已招"+confirmedNum+"人/共需"+num+"人");
    }
    @BindingAdapter("needAndAll")
    public static void needAndAll(TextView tv, JobBean item){
        int joinedNum = item.getConfirmedNum();
        int num = item.getNum();
        tv.setText("已招"+joinedNum+"人/共需"+num+"人");
    }

    /**
     * 工期中3，待结算4状态为：确认上工人数confirmedNum ，所需工人数num ,工价/天price
     *  其他状态：报名工人数tobeConfirmNum，所需工人数num，已雇佣人数confirmedNum
     */
    @BindingAdapter("getWorkersInfoTop")
    public static void getWorkersInfoTop(LinearLayout layout, MyGetWorkersResponse.ItemsBean item){
        if (item==null)return;
        SpannableStringBuilder str0,str1,str2;
        TextView tv0 = (TextView) layout.getChildAt(0);
        TextView tv1 = (TextView) layout.getChildAt(1);
        TextView tv2 = (TextView) layout.getChildAt(2);
        int itemType = item.getItemType();
        if (itemType==3||itemType==4){
            int confirmedNum = item.getConfirmedNum();
            int num = item.getNum();
            String price = item.getPrice();
            str0 = createSpan(confirmedNum+"","确认上工人数");
            str1 = createSpan(num+"","所需工人数");
            str2 = createSpan("￥"+price,"工价/天");
        }else {

            int tobeConfirmNum = item.getTobeConfirmNum();
            int num = item.getNum();
            int confirmedNum = item.getConfirmedNum();
            str0 = createSpan(tobeConfirmNum+"","报名工人数");
            str1 = createSpan(num+"","所需工人数");
            str2 = createSpan(confirmedNum+"","已雇佣人数");
        }
        tv0.setText(str0);
        tv1.setText(str1);
        tv2.setText(str2);


    }
    private static SpannableStringBuilder createSpan(String s1,String s2){
        return SpannableStringUtils.getBuilder( s1+ "\n").setBold().setProportion(1.3f).setForegroundColor(Color.BLACK)
                .append(s2)
                .create();
    }

    //空闲工人：18人（团队）\n空闲时间：2020-10-29至2020-11-29（共30天）
    @BindingAdapter("freeTimePersion")
    public static void freeTimePersion(TextView tv, WorkerBean item){
        int freePersionNum = item.getNum();
        String startTime = item.getStartDate();
        String endTime = item.getEndDate();
        int day = item.getDay();
        tv.setText(String.format(Locale.CHINA,"空闲工人：%d人（%s）\n空闲时间：%s至%s（共%d天）", freePersionNum, freePersionNum > 1 ? "团队" : "个人", startTime, endTime, day));
    }

    /**
     *  1:正在招
     *  2:已招满/待开工(所有用户已确认)
     *  3:工期中
     *  4:待结算
     *  5:待评价
     *  6:已完成
     *  7:已取消
     *  8:已过期
     */
    @BindingAdapter("workerType")
    public static void workerType(TextView tv, int type){

        switch (type){
            case 2:
                tv.setText("待开工");
            break;
            case 3:
                tv.setText("上工中");
            break;
            case 4:
                tv.setText("待结算");
            break;
            case 5:
                tv.setText("待评价");
            break;
            case 6:
                tv.setText("已完成");
            break;
            case 7:
                tv.setText("已取消");
            break;
            default:
                tv.setText("等待上工");
                break;
        }

    }
}
