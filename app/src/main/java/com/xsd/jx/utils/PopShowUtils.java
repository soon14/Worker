package com.xsd.jx.utils;

import android.view.View;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.custom.BottomSharePop;
import com.xsd.jx.custom.InviteJobPop;
import com.xsd.jx.custom.PushJobPop;
import com.xsd.jx.mine.RealNameAuthActivity;
import com.xsd.utils.SPUtils;
import com.xsd.utils.SmallUtils;

import java.util.List;

/**
 * Date: 2020/9/10
 * author: SmallCake
 * 各种弹框提示
 * 和
 * 数据卡片弹框提示
 */
public class PopShowUtils {
    /**
     * 首页显示推荐的工作弹框
     */
    public static void showPushJob(BaseActivity activity) {
        new XPopup.Builder(activity)
                .asCustom(new PushJobPop(activity))
                .show();
    }

    /**
     * 弹出被邀请的工作
     */
    public static void showInviteJob(List<JobBean> data,BaseActivity activity) {
        new XPopup.Builder(activity)
                .asCustom(new InviteJobPop(activity,data))
                .show();
    }

    /**
     * 底部分享弹框
     * 目前只有微信和微信朋友圈
     */
    public static void showShare(BaseActivity activity) {
        new XPopup.Builder(activity)
                .asCustom(new BottomSharePop(activity))
                .show();
    }

    /**
     * 提示弹框
     * 如果点击不再提示，就不再弹出此窗口提示
     * @param v
     */
    public static void showTips(View v) {
//        boolean no_tips_joinsuccess = (boolean) SPUtils.get("no_tips_joinsuccess", false);
//        if (no_tips_joinsuccess)return;
        new XPopup.Builder(SmallUtils.getApp().getApplicationContext())
                .asConfirm("报名上工提醒",
                        "您已成功报名上工，请耐心等待企业确认您的上工申请，企业确认后您才可有效上工。",
                        "不在提示",
                        "知道了",
                        null,
                        new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                SPUtils.put("no_tips_joinsuccess",true);
                            }
                        },
                        false,
                        R.layout.dialog_tips)
                .show();
    }

    public static void showRealNameAuth(View v,BaseActivity activity) {
        new XPopup.Builder(v.getContext())
                .asConfirm("实名认证提醒",
                        "根据国家政策规定，您需要先完成实名认证才可上工。",
                        "取消",
                        "实名认证提醒",
                        () -> activity.goActivity(RealNameAuthActivity.class),
                        null,
                        true,R.layout.dialog_tips)
                .show();

    }
}
