package com.beanu.l2_imageselector;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yuyh.library.imgsel.common.ImageLoader;

/**
 * 图片加载器
 * Created by Beanu on 2016/12/15.
 */

public class GlideLoader implements ImageLoader, AlbumLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }


    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        Glide.with(imageView).load(albumFile.getPath()).into(imageView);

    }

    @Override
    public void load(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }
}
