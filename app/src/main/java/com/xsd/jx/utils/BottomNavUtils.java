package com.xsd.jx.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.xsd.jx.R;
import com.xsd.jx.listener.OnBottomNavClickListener;

/**
 * Date: 2020/8/17
 * author: SmallCake
 */
public class BottomNavUtils {
    private static int[] tabSelectedImgs = new int[]{R.mipmap.icon_selected_tab1, R.mipmap.icon_selected_tab2, R.mipmap.icon_selected_tab3};
    private static int[] tabUnselectedImgs = new int[]{R.mipmap.icon_unselected_tab1, R.mipmap.icon_unselected_tab2, R.mipmap.icon_unselected_tab3};

    public static void initTabBindViewPager(LinearLayout tabLayout,ViewPager viewPager, OnBottomNavClickListener listener) {
        int childCount = tabLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout childAt = (LinearLayout) tabLayout.getChildAt(i);
            int finalI = i;
            childAt.setOnClickListener(v ->{
                if (listener!=null)listener.onNavClick(finalI);
                toDefaultTab(finalI,tabLayout,viewPager);
            } );
        }
    }

    /**
     * Tab选择事件
     * 1.还原所有Tab为默认状态
     * 2.选中项加入白色背景和替换为选中图片
     * 3.联动ViewPager
     * @param selectPosition 选中项
     */
    public static void toDefaultTab(int selectPosition, LinearLayout tabLayout, ViewPager viewPager) {
        int childCount = tabLayout.getChildCount();
        ImageView ivTab;
        for (int i = 0; i < childCount; i++) {
            //恢复默认图标
            LinearLayout childAt = (LinearLayout) tabLayout.getChildAt(i);
            View view = childAt.getChildAt(0);
            if (view instanceof ImageView) {
                ivTab = (ImageView) view;
                ivTab.setImageResource(tabUnselectedImgs[i]);
            } else if (view instanceof RelativeLayout) {
                RelativeLayout layout = (RelativeLayout) view;
                ivTab = (ImageView) layout.getChildAt(0);
                ivTab.setImageResource(tabUnselectedImgs[i]);
            }
            //恢复默认字体颜色
            TextView tvTab = (TextView) childAt.getChildAt(1);
            tvTab.setTextColor(ContextCompat.getColor(tabLayout.getContext(), R.color.gray));
        }

        //选中项替换选中图标
        LinearLayout childAt = (LinearLayout)tabLayout.getChildAt(selectPosition);
        AnimUtils.tabSelect(childAt);
        View view = childAt.getChildAt(0);
        if (view instanceof ImageView) {
            ivTab = (ImageView) view;
            ivTab.setImageResource(tabSelectedImgs[selectPosition]);
        } else if (view instanceof RelativeLayout) {
            RelativeLayout layout = (RelativeLayout) view;
            ivTab = (ImageView) layout.getChildAt(0);
            ivTab.setImageResource(tabSelectedImgs[selectPosition]);
        }
        //恢复默认字体颜色
        TextView tvTab = (TextView) childAt.getChildAt(1);
        tvTab.setTextColor(ContextCompat.getColor(tabLayout.getContext(), R.color.black));
        viewPager.setCurrentItem(selectPosition);
    }

}
