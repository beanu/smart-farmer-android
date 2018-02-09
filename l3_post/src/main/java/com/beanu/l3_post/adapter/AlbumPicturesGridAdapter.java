package com.beanu.l3_post.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.beanu.l3_post.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.AlbumFile;

import java.util.List;

/**
 *
 * @author lizhi
 * @date 2017/9/13
 *
 */

public class AlbumPicturesGridAdapter extends BasePicturesGridAdapter<AlbumFile> {

    private RequestOptions placeHolder = RequestOptions.placeholderOf(R.drawable.img_placeholder);

    public AlbumPicturesGridAdapter(Context context, List<AlbumFile> pictures, boolean showAddBtn, ActionHandler<AlbumFile> actionHandler) {
        super(context, pictures, showAddBtn, actionHandler);
    }

    @Override
    public void displayImage(AlbumFile item, ImageView imageView) {
        Glide.with(context).load(item.getPath()).apply(placeHolder).into(imageView);
    }

    @Override
    public boolean isVideo(AlbumFile item) {
        return item.getMimeType() != null && item.getMimeType().contains("video");
    }
}
