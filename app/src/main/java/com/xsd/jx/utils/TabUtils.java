package com.xsd.jx.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.xsd.jx.R;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.utils.DpPxUtils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.List;

/**
 * Date: 2020/1/8
 * author: SmallCake
 * 通用Tab创建工具类
 */
public class TabUtils {
    /**
     * 不需要和ViewPager关联，把点击事件提取出来
     * @param context 上下文
     * @param magicIndicator Tab控件
     * @param tabNames Tab标题
     * @param listener 监听
     */
    public static void setDefaultTab(Context context, MagicIndicator magicIndicator, List<String> tabNames, OnTabClickListener listener) {
        FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);//铺满平分整个TabLayout
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames == null ? 0 : tabNames.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.tv_black));//默认颜色
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.colorAccent));//选中颜色
                colorTransitionPagerTitleView.setTextSize(18);
//                colorTransitionPagerTitleView.getPaint().setFakeBoldText(true);

                int pading = DpPxUtils.dp2px(16);
                colorTransitionPagerTitleView.setPadding(pading,0,pading,0);
                colorTransitionPagerTitleView.setText(tabNames.get(index));
                colorTransitionPagerTitleView.setOnClickListener(view -> {
                    mFragmentContainerHelper.handlePageSelected(index);
                    if (listener!=null)listener.onTabClick(index);
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 4));//y轴向上偏移8个dp
                indicator.setLineWidth(DpPxUtils.dp2px(24));//指示器线条宽度
                indicator.setColors(ContextCompat.getColor(context, R.color.colorAccent));//指示器线条颜色，可设置多个不同颜色
                indicator.setRoundRadius(DpPxUtils.dp2px(3));//指示器线条圆角幅度
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
    }
    public static void setDefaultTab(Context context, MagicIndicator magicIndicator, List<String> tabNames, ViewPager viewPager) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);//铺满平分整个TabLayout
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames == null ? 0 : tabNames.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.colorPrimary));//默认颜色
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.colorAccent));//选中颜色
                colorTransitionPagerTitleView.setTextSize(18);
                int pading = DpPxUtils.dp2px(16);
                colorTransitionPagerTitleView.setPadding(pading,0,pading,0);
                colorTransitionPagerTitleView.setText(tabNames.get(index));
                colorTransitionPagerTitleView.setOnClickListener(view -> {
                    viewPager.setCurrentItem(index);
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 4));//y轴向上偏移8个dp
                indicator.setLineWidth(DpPxUtils.dp2px(24));//指示器线条宽度
                indicator.setColors(ContextCompat.getColor(context, R.color.colorAccent));//指示器线条颜色，可设置多个不同颜色
                indicator.setRoundRadius(DpPxUtils.dp2px(3));//指示器线条圆角幅度
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
