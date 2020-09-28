package com.xsd.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project20180408 --  cn.com.smallcake_utils
 * Created by Small Cake on  2018/5/4 15:50.
 * 分享工具类
 * <p>
 * 注意：
 * 1.分享图片需要
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <p>
 * 2.由于分享功能是使用隐式调用Activtiy实现的，则需在响应的Activity中声明intent-filter，在对应的activity的xml里加上
 * <p>
 * <intent-filter>
 * <action android:name="android.intent.action.SEND" />
 * <category android:name="android.intent.category.DEFAULT" />
 * </intent-filter>
 * <p>
 * 3.开启另一个分享Acivity需要Activity的上下文
 */
public class ShareUtils {
    /**
     * 分享文字内容
     *
     * @param dlgTitle 分享对话框标题
     * @param subject  主题
     * @param content  分享内容（文字）
     */
    public static void shareText(Activity activity, String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            activity.startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            activity.startActivity(intent);
        }
    }

    /**
     * 分享图片和文字内容
     *
     * @param dlgTitle 分享对话框标题 ：弹出的分享Dialog上面标题
     * @param subject  主题：一般只有发送邮件才会用到此参数
     * @param content  分享内容（文字）：微博才有
     * @param uri      图片资源URI
     */
    public static void shareImg(Activity activity, String dlgTitle, String subject, String content,
                                Uri uri) {
        if (uri == null) return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (!TextUtils.isEmpty(subject)) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (!TextUtils.isEmpty(content)) intent.putExtra(Intent.EXTRA_TEXT, content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            activity.startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            activity.startActivity(intent);
        }
    }
    public static void shareImgToWechat(Activity activity, String dlgTitle, String subject, String content,
                                        Uri uri) {
        if (uri == null) return;
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        Intent shareIntent = new Intent();
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        if (!TextUtils.isEmpty(subject)) shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (!TextUtils.isEmpty(content)) shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (TextUtils.isEmpty(dlgTitle)) { // 自定义标题
            activity.startActivity(Intent.createChooser(shareIntent, dlgTitle));
        } else { // 系统默认标题
            activity.startActivity(shareIntent);
        }
    }


    public static void shareImg(Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        Uri uri = saveBitmap(activity,bitmap, content);
        if (uri == null) return;
        shareImg(activity, dlgTitle, subject, content, uri);
    }
    //分享图片到微信
    public static void shareImgToWechatMoments(Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        shareImgToOtherApp(0,activity,dlgTitle,subject,content,bitmap);
    }
    public static void shareImgToWechat(Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        shareImgToOtherApp(1,activity,dlgTitle,subject,content,bitmap);
    }
    public static void shareImgToQQ(Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        shareImgToOtherApp(2,activity,dlgTitle,subject,content,bitmap);
    }
    public static void shareImgToSina(Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        shareImgToOtherApp(3,activity,dlgTitle,subject,content,bitmap);
    }

    /**
     * 注意：qq空间页面activity不对外开放
     * 所以qq空间分享只支持sdk分享，不支持原生qq空间分享
     */
    public static void shareImgToOtherApp(int type,Activity activity, String dlgTitle, String subject, String content, Bitmap bitmap) {
        Uri uri = saveBitmap(activity,bitmap, content);
        if (uri == null) return;
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        switch (type){
            case 0://微信朋友圈
                comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                break;
            case 1://微信个人
                comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                break;
            case 2://QQ好友
                comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                break;
//            case 3://QQ空间:qq空间页面activity不对外开放
//                comp = new ComponentName("com.qzone", "com.qzonex.module.maxvideo.activity.QzonePublishVideoActivity");
//                break;
            case 3:
                comp = new ComponentName("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity");
                break;
        }
        Intent shareIntent = new Intent();
        shareIntent.setComponent(comp);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        if (!TextUtils.isEmpty(subject)) shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (!TextUtils.isEmpty(content)) shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            if (TextUtils.isEmpty(dlgTitle)) { // 自定义标题
                activity.startActivity(Intent.createChooser(shareIntent, dlgTitle));
            } else { // 系统默认标题
                activity.startActivity(shareIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showLong("没有安装此应用！");
        }
    }

    /**
     * 将图片存到本地 不能保存到缓存路径，因为只有本应用才能访问
     */
    private static Uri saveBitmap(Activity activity,Bitmap bm, String imgName) {
        try {
            String cacheDir = activity.getExternalCacheDir().getPath();

            String dir = cacheDir + TimeUtils.getTime() + "_" + imgName + ".jpg";
            File f = new File(dir);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Uri uri;
            //7.0以上需要添加临时读取权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(activity, AppUtils.getAppPackageName()+".fileprovider", f);
            } else {
                uri = Uri.fromFile(f);
            }
            return uri;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 分享多张图片
     */

    public static void shareMultipleImage(Activity activity, File... files) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (File file : files) uriList.add(Uri.fromFile(file));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public static void shareTxtAndImg(Activity activity, String activityTitle, String msgTitle, String msgText,
                                      String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activityTitle));
    }

    public static void shareTxtAndImg(Activity activity, String activityTitle, String msgTitle, String msgText,
                                      int imgRes) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + activity.getResources().getResourcePackageName(imgRes) + "/"
                + activity.getResources().getResourceTypeName(imgRes) + "/"
                + activity.getResources().getResourceEntryName(imgRes));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activityTitle));
    }

}
