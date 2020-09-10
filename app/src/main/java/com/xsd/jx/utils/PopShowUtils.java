package com.xsd.jx.utils;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.custom.PushJobPop;

/**
 * Date: 2020/9/10
 * author: SmallCake
 * 各种弹框提示
 * 和
 * 数据卡片弹框提示
 */
public class PopShowUtils {

    public static void showPushJob(BaseActivity activity) {
        new XPopup.Builder(activity)
                .asCustom(new PushJobPop(activity))
                .show();
    }
}
