package com.xsd.jx.utils;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BannerBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner工具类
 */
public class BannerUtils {
    static  int bannerHeight;//banner的高度：1142*380
    static {
          bannerHeight  = (int) ((ScreenUtils.getRealWidth()-DpPxUtils.dp2px(16))*(380f/1142f));
    }

    //通过广告数据动态创建Banner
    public static void initBanner(Banner banner, List<BannerBean> banners){
        if (banners==null||banners.size()==0)return;
        List<String>imgBeans = new ArrayList<>();
        for (BannerBean bannerBean : banners) imgBeans.add(bannerBean.getContentPath());
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams){
            banner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bannerHeight));
        }else if (layoutParams instanceof RelativeLayout.LayoutParams){
            banner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, bannerHeight));
        }else if (layoutParams instanceof ConstraintLayout.LayoutParams){
            banner.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, bannerHeight));
        }else if (layoutParams instanceof CollapsingToolbarLayout.LayoutParams){
            CollapsingToolbarLayout.LayoutParams layoutMain = new CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT, bannerHeight);
            int dp8 = DpPxUtils.dp2px(8);
            layoutMain.setMargins(dp8,dp8*4,dp8,dp8);
            banner.setLayoutParams(layoutMain);
        }
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new BannerImgLoader());
        banner.setDelayTime(5000);
        banner.setImages(imgBeans);
        banner.start();
        banner.setOnBannerListener(position -> {
        });
    }
    public static void bindBanner(BaseActivity activity,int tId,Banner banner){
        activity.getDataProvider().site.banner(tId)
                .subscribe(new OnSuccessAndFailListener<BaseResponse>() {
                    @Override
                    protected void onSuccess(BaseResponse baseResponse) {


                    }
                });
    }




}
