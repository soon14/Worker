package com.xsd.jx.utils;

import com.xsd.utils.SmallUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 2020/8/26
 * author: SmallCake
 */
public class FileNameUtils {
    public static String getFileName() {
        String saveDir = SmallUtils.getApp().getExternalCacheDir().getPath();
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir(); // 创建文件夹
        }
        //用日期作为文件名，确保唯一性
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = saveDir + "/" + formatter.format(date) + ".jpg";
        return fileName;
    }
}
