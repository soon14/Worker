package com.xsd.jx.utils;

import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.xsd.jx.MyApplication;
import com.xsd.jx.base.BaseActivity;

import java.util.List;

/**
 * Date: 2020/6/8
 * author: SmallCake
 * 阿里云视频上传工具类
 */
public class AliVodUtils {
    private static VODUploadClient vodUploadClient;
    private static int uploadVideNum =0;//上传的数量

    private static VODUploadClient getClient() {
        if (vodUploadClient == null) {
            synchronized (VODUploadClient.class) {
                if (vodUploadClient == null)  vodUploadClient = new VODUploadClientImpl(MyApplication.getInstance());
            }
        }
        return vodUploadClient;
    }

    public interface VodUpListener{
         void onUploadSucceed(UploadFileInfo info, String videoId);
         void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize);
    }

    /**
     * 上传多张图片，完成后的监听
     */
    public interface UploadAllVideosListener {
        void onUpLoadComplete(List<String> videoIds);
    }

    /**
     * 获取授权然后进行视频上传，并回调
     * 上传单个视频
     *  Deprecated param type 1.活动视频 2.服务者相册视频 3.消费者动态视频4.录制音频
     * @param type 位置：1.服务者相册视频 2.消费者动态视频
     */
    public static void authAndUpVoice(BaseActivity activity,  VodUpListener listener){


    }



}
