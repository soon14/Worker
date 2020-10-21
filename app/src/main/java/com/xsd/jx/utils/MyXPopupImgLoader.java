package com.xsd.jx.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.xsd.jx.R;

import java.io.File;

/**
 * Date: 2020/1/7
 * author: SmallCake
 * Banner图片加载器
 */
public class MyXPopupImgLoader implements XPopupImageLoader {

    @Override
    public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
        if (uri==null)return;
        RequestOptions options= new RequestOptions()
                .transform(new CenterCrop(),new RoundedCorners(6))
                .placeholder(R.drawable.no_banner)
                .error(R.drawable.no_banner);
        Glide.with(imageView.getContext()).load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public File getImageFile(@NonNull Context context, @NonNull Object uri) {
        return null;
    }
}
