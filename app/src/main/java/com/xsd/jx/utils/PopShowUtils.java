package com.xsd.jx.utils;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.LinearLayout;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lzf.easyfloat.EasyFloat;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobBean;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.VersionResponse;
import com.xsd.jx.custom.BottomNationSelecterPop;
import com.xsd.jx.custom.BottomProvincesPop;
import com.xsd.jx.custom.BottomSharePop;
import com.xsd.jx.custom.BottomSingleWorkTypePop;
import com.xsd.jx.custom.InviteJobPop;
import com.xsd.jx.custom.PushJobPop;
import com.xsd.jx.listener.OnAddrListener;
import com.xsd.jx.listener.OnNationSelectListener;
import com.xsd.jx.listener.OnWorkTypeSelectListener;
import com.xsd.jx.mine.RealNameAuthActivity;
import com.xsd.utils.AppUtils;
import com.xsd.utils.FormatUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.SPUtils;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.SpannableStringUtils;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.List;

import me.jessyan.progressmanager.body.ProgressInfo;

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
//                .setPopupCallback(new SimpleCallback(){
//                    @Override
//                    public void onShow(BasePopupView popupView) {
//                        super.onShow(popupView);
//                        EasyFloat.hideAppFloat();
//                    }
//
//                    @Override
//                    public void onDismiss(BasePopupView popupView) {
//                        super.onDismiss(popupView);
//                        EasyFloat.showAppFloat();
//                    }
//                })
                .asCustom(new PushJobPop(activity))
                .show();
    }

    /**
     * 弹出被邀请的工作
     */
    public static void showInviteJob(List<JobBean> data, BaseActivity activity) {
        new XPopup.Builder(activity)
                .setPopupCallback(new SimpleCallback(){
                    @Override
                    public void onShow(BasePopupView popupView) {
                        super.onShow(popupView);
                        EasyFloat.hideAppFloat();
                    }

                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        EasyFloat.showAppFloat();
                    }
                })
                .asCustom(new InviteJobPop(activity, data))
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
     */
    public static void showTips(BaseActivity activity) {
//        boolean no_tips_joinsuccess = (boolean) SPUtils.get("no_tips_joinsuccess", false);
//        if (no_tips_joinsuccess)return;
        new XPopup.Builder(activity)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asConfirm("报名上工提醒",
                        "您已成功报名上工，请耐心等待企业确认您的上工申请，企业确认后您才可有效上工。",
                        "不在提示",
                        "知道了",
                        null,
                        new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                SPUtils.put("no_tips_joinsuccess", true);
                            }
                        },
                        false,
                        R.layout.dialog_tips)
                .show();
    }

    public static void showMsg(BaseActivity activity,String msg) {
        new XPopup.Builder(activity)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asConfirm("提示",
                        msg,
                        "",
                        "知道了",
                        null,
                        null,
                        true,
                        R.layout.dialog_tips)
                .show();
    }


    public static void showAppUpdate(BaseActivity activity) {
        activity.getDataProvider().site.checkVersion()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<VersionResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<VersionResponse> baseResponse) {
                        VersionResponse data = baseResponse.getData();
                        boolean isMust = data.getIs_must() == 1;//是否强制更新
                        String desc = data.getDesc();
                        String down_url = data.getUrl();
                        int version = data.getVersion();
                        if (version<=AppUtils.getVersionCode()) return;
                        SpannableStringBuilder content = SpannableStringUtils.getBuilder("更新内容")
                                .setForegroundColor(Color.parseColor("#333333"))
                                .setProportion(1.1f)
                                .append(desc)
                                .create();
                        new XPopup.Builder(activity)
                                .setPopupCallback(new SimpleCallback(){
                                    @Override
                                    public void onShow(BasePopupView popupView) {
                                        super.onShow(popupView);
                                        EasyFloat.hideAppFloat();
                                    }

                                    @Override
                                    public void onDismiss(BasePopupView popupView) {
                                        super.onDismiss(popupView);
                                        EasyFloat.showAppFloat();
                                    }
                                })
                                .dismissOnBackPressed(false)
                                .dismissOnTouchOutside(false)
                                .asConfirm("发现新版本",
                                        content,
                                        "稍后更新",
                                        "立即更新",
                                        () -> {
                                            downloadApk(activity,down_url);
                                        },
                                        null,
                                        isMust, R.layout.dialog_update)

                                .show();
                    }

                    @Override
                    protected void onErr(String err) {
                    }
                });


    }

    private static void downloadApk(Activity context, String down_url) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("进度更新");
        progressDialog.show();
        String path = context.getExternalCacheDir().getAbsolutePath()+ File.separator;
        DownloadUtils.getInstance().download(down_url, path, "jiangxin.apk", new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                L.i("恭喜你下载成功，开始安装！==" + path + "jiangxin.apk");
                ToastUtil.showShort("恭喜你下载成功，开始安装！");
                String successDownloadApkPath = path + "jiangxin.apk";
                AppUtils.installApk(context, successDownloadApkPath);
            }
            @Override
            public void onDownloading(ProgressInfo progressInfo) {
                progressDialog.setProgress(progressInfo.getPercent());
                boolean finish = progressInfo.isFinish();
                if (!finish) {
                    long speed = progressInfo.getSpeed();
                    progressDialog.setMessage("下载速度(" + (speed > 0 ? FormatUtils.formatSize(context, speed) : speed) + "/s)");
                } else {
                    progressDialog.setMessage("下载完成！");
                }
            }
            @Override
            public void onDownloadFailed() {
                progressDialog.dismiss();
                ToastUtil.showShort("下载失败！");
            }
        });

    }
    public static void showRealNameAuth(BaseActivity activity) {
        new XPopup.Builder(activity)
                .asConfirm("实名认证提醒",
                        "根据国家政策规定，您需要先完成实名认证才可上工。",
                        "取消",
                        "马上实名认证",
                        () -> activity.goActivity(RealNameAuthActivity.class),
                        null,
                        true, R.layout.dialog_tips)
                .show();
    }
    public static void showConfirm(BaseActivity activity, String content,String cancelBtnText,String confirmBtnText, OnConfirmListener listener) {
        new XPopup.Builder(activity)
                .asConfirm("提醒",
                        content,
                        cancelBtnText,
                        confirmBtnText,
                         listener,
                        null,
                        false, R.layout.dialog_confirm)
                .show();
    }


    public static void showDelWorkType(BaseActivity activity, int id, LinearLayout layoutTypesWork, View viewType) {
        new XPopup.Builder(activity)
                .asConfirm("提醒",
                        "您是否确定删除该项？",
                        "取消",
                        "确定",
                        () -> {
                            activity.getDataProvider().work.workTypeRem(id)
                                    .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(activity.getDialog()) {
                                        @Override
                                        protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                            viewType.animate()
                                                    .setListener(new Animator.AnimatorListener() {
                                                        @Override
                                                        public void onAnimationStart(Animator animation) {
                                                        }

                                                        @Override
                                                        public void onAnimationEnd(Animator animation) {
                                                            layoutTypesWork.removeView(viewType);
                                                        }

                                                        @Override
                                                        public void onAnimationCancel(Animator animation) {

                                                        }

                                                        @Override
                                                        public void onAnimationRepeat(Animator animation) {

                                                        }
                                                    }).translationX(ScreenUtils.getRealWidth()).alpha(0f).start();
                                        }
                                    });
                        },
                        null,
                        false, R.layout.dialog_tips)
                .show();
    }

    /**
     * 工作经验选择
     *
     * @param v
     */
    public static void showWorkExp(View v, OnSelectListener listener) {
        new XPopup.Builder(v.getContext())
                .asBottomList("工龄选择",
                        new String[]{"1~5年", "5~10年", "10~20年"},
                        null,
                        -1,
                        false,
                        listener,
                        0,
                        0).show();
    }
    public static void showBankName(View v, OnSelectListener listener) {
        new XPopup.Builder(v.getContext())
                .asBottomList("银行选择",
                        new String[]{"中国工商银行","中国农业银行","中国银行","中国建设银行","交通银行","招商银行","中信银行","光大银行","兴业银行","中国邮政储蓄"},
                        null,
                        -1,
                        false,
                        listener,
                        0,
                        0).show();
    }
    /**
     *身份 1:建筑企业 2:劳务公司 3:个人
     */
    public static void showIdTypes(View v, OnSelectListener listener) {
        new XPopup.Builder(v.getContext())
                .asBottomList("你的身份",
                        new String[]{"建筑企业", "劳务公司", "个人"},
                        null,
                        -1,
                        false,
                        listener,
                        0,
                        0).show();
    }
    /**
     * 合作意向 1:项目入股 2:资源合作 3:委托招工
     */
    public static void showPurpose(View v, OnSelectListener listener) {
        new XPopup.Builder(v.getContext())
                .asBottomList("合作意向",
                        new String[]{"项目入股", "资源合作", "委托招工"},
                        null,
                        -1,
                        false,
                        listener,
                        0,
                        0).show();
    }

    /**
     * 名族选择
     * @param activity
     * @param listener
     */
    public static void showNationList(BaseActivity activity, OnNationSelectListener listener) {
        new XPopup.Builder(activity)
                .asCustom(new BottomNationSelecterPop(activity, listener))
                .show();
    }

    /**
     * 选择单个工种的底部弹框
     */
    public static void showWorkTypeSelect(BaseActivity baseActivity,OnWorkTypeSelectListener listener){
        new XPopup.Builder(baseActivity)
                .asCustom(new BottomSingleWorkTypePop(baseActivity, listener))
                .show();
    }

    /**
     * 省市区地址选择器
     * 目前为湖北省下的市和区的选择
     * @param baseActivity
     * @param listener
     */
    public static void showBottomAddrSelect(BaseActivity baseActivity,OnAddrListener listener){
        BottomProvincesPop bottomProvincesPop = new BottomProvincesPop(baseActivity);
        bottomProvincesPop.setListener(listener);
            new XPopup.Builder(baseActivity)
                    .asCustom(bottomProvincesPop)
                    .show();


    }

    /***
     * 联系平台
     * @param actiivty
     */
    public static void callUs(Activity actiivty){
        String platPhone = (String) SPUtils.get("platPhone", "");
        MobileUtils.callPhone(actiivty,platPhone);
    }
}
