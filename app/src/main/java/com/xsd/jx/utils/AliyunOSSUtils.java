package com.xsd.jx.utils;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.xsd.jx.MyApplication;
import com.xsd.jx.base.BaseActivity;
import com.xsd.utils.FormatUtils;
import com.xsd.utils.L;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.xsd.jx.base.MyOSSConfig.BUCKET_NAME;

/**
 * Date: 2020/9/28
 * author: SmallCake
 */
public class AliyunOSSUtils {
    private static AliyunOSSUtils mOSSUtils;
    private static OSS oss;
    private int upLoadNum = 0;//上传的数量

    public static final String USER_AVATARS = "user-avatars/";
    public static final String SERVANT_AVATARS = "servant-avatars/";
    public static final String SERVANT_CERT = "servant-cert/";
    public static final String GALLERY = "gallery/";
    public static final String BLOG = "blog/";
    public static final String ORDER_COMMENT = "order-comment/";
    public static final String REPORT = "report/";
    public static final String CHAT = "chat/";

    /**
     * 单一实例
     */
    public static AliyunOSSUtils getInstance() {
        if (mOSSUtils == null) {
            synchronized (AliyunOSSUtils.class) {
                if (mOSSUtils == null) mOSSUtils = new AliyunOSSUtils();
            }
        }
        return mOSSUtils;
    }

    public AliyunOSSUtils() {
        oss = MyApplication.getInstance().getOss();
    }

    /**
     * 上传头像
     *
     *  type      1、消费者2、服务者 合并，现在只有消费者1
     * @param localPath 本地要上次的文件地址
     * @return
     */
    public void uploadAvatar(BaseActivity baseActivity, String localPath, UploadImgListener listener) {
//        String aliFolder = (type == 1 ? USER_AVATARS : SERVANT_AVATARS);
        String aliFolder =  USER_AVATARS;
        this.uploadImg(baseActivity, aliFolder, localPath, listener);
    }

    /**
     * 聊天上传图片
     */
    public void uploadChat(BaseActivity baseActivity, String localPath, UploadImgListener listener) {
        this.uploadImg(baseActivity, CHAT, localPath, listener);
    }


    /**
     * 上传单张图片的监听
     */
    public interface UploadImgListener {
        void onUpLoadComplete(String url);

        void onUpLoadProgress(int progress);
    }

    /**
     * 上传多张图片，完成后的监听
     */
    public interface UploadAllImgsListener {
        void onUpLoadComplete(List<String> isUpUrls);
    }

    /**
     * 上传多张图片
     *
     * @param aliFolder 上传图片到阿里云的空间文件夹地址
     * @param activity  上下文
     * @param datas     本地图片地址集
     * @param listener  监听所有图片
     */
    public void uploadImgs(String aliFolder, BaseActivity activity, List<String> datas, UploadAllImgsListener listener) {
        upLoadNum = 0;
        List<String> isUpUrls = new ArrayList<>();
        ProgressDialog progressDialog;
        if (datas.size() > 0) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("图片正在上传中，请等待");
            progressDialog.show();
            String path = datas.get(upLoadNum);
            uploadLoop(aliFolder, activity, path, isUpUrls, datas, progressDialog, listener);
        } else {
            //upload complete
            if (listener != null) listener.onUpLoadComplete(isUpUrls);
        }
    }

    public void uploadImgsReport(String aliFolder, BaseActivity activity, List<String> picPaths, UploadAllImgsListener listener) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < picPaths.size(); i++) {
            String path = picPaths.get(i);
            if (!TextUtils.isEmpty(path)) datas.add(path);
        }
        upLoadNum = 0;
        List<String> isUpUrls = new ArrayList<>();
        ProgressDialog progressDialog;
        if (datas.size() > 0) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("图片正在上传中，请等待");
            progressDialog.show();
            String path = datas.get(upLoadNum);
            uploadLoop(aliFolder, activity, path, isUpUrls, datas, progressDialog, listener);
        } else {
            //upload complete
            if (listener != null) listener.onUpLoadComplete(isUpUrls);
        }
    }

    /**
     * 嵌套单张图片上传方法 {@link AliyunOSSUtils#uploadImg(BaseActivity, String, String, UploadImgListener)}
     * 循环回调自己，从而实现多图队列上传。
     *
     * @param aliFolder      上传图片到阿里云的空间文件夹地址
     * @param activity       上下文
     * @param locaPath       当前要上传单张本地图片地址
     * @param isUpUrls       已经上传的图片地址收集集合
     * @param locaImgs       本地要上传的图片集合
     * @param progressDialog 进度显示dialog
     * @param listener       监听所有图片上传完成，并把所有上传完成后的图片地址 {@param isUpUrls}传递给页面
     */
    private void uploadLoop(String aliFolder, BaseActivity activity, String locaPath, List<String> isUpUrls, List<String> locaImgs, ProgressDialog progressDialog, UploadAllImgsListener listener) {
        AliyunOSSUtils.getInstance().uploadImg(activity, aliFolder, locaPath, new AliyunOSSUtils.UploadImgListener() {
            @Override
            public void onUpLoadComplete(String url) {
                isUpUrls.add(url);
                upLoadNum++;
                if (upLoadNum < locaImgs.size()) {
                    String path = locaImgs.get(upLoadNum);
                    uploadLoop(aliFolder, activity, path, isUpUrls, locaImgs, progressDialog, listener);
                } else {
                    //upload complete
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (listener != null) listener.onUpLoadComplete(isUpUrls);
                }
            }

            @Override
            public void onUpLoadProgress(int progress) {
                if (progressDialog != null) {
                    progressDialog.setTitle("上传中，请等待(" + (upLoadNum + 1) + ")");
                    progressDialog.setProgress(progress);
                }
            }
        });
    }


    /**
     * 上传单张图片
     *
     * @param activity    页面
     * @param aliFolder   文件夹名称
     * @param locaImgPath 本地文件路径
     * @param listener    监听上传进度
     */
    private void uploadImg(BaseActivity activity, String aliFolder, String locaImgPath, UploadImgListener listener) {
        String objectKey = aliFolder + getUpFileName(locaImgPath);
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, objectKey, locaImgPath);
        put.setProgressCallback((request, currentSize, totalSize) -> {
            int progress = FormatUtils.getProgress(currentSize, totalSize);
//            L.d("PutObject", "上传进度 " + progress + "%");
            activity.runOnUiThread(() -> {
                if (listener != null) listener.onUpLoadProgress(progress);
            });
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String url = oss.presignPublicObjectURL(BUCKET_NAME, objectKey);
                L.e("上传后的地址=="+url);
                activity.runOnUiThread(() -> {
                    if (listener != null) listener.onUpLoadComplete(url);
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    /**
     * 通过雪花算法拼接上传文件名称，并拼接宽高
     */
    @NotNull
    private String getUpFileName(String localPath) {
        String endName = localPath.substring(localPath.lastIndexOf("."));
        //获取图片的宽高
        SnowFlakeWorkId snowId = new SnowFlakeWorkId();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//仅做解码处理，不加载到内存
        BitmapFactory.decodeFile(localPath, options);//解析文件
        //获取图片宽高
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        return snowId.nextId() + "_" + imgWidth + "x" + imgHeight + endName;
    }


}
