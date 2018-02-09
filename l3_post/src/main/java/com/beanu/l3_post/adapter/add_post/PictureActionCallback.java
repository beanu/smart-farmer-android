package com.beanu.l3_post.adapter.add_post;

import com.beanu.l3_post.model.bean.PostContent;
import com.yanzhenjie.album.AlbumFile;

/**
 * @author lizhi
 * @date 2017/11/14.
 */

public interface PictureActionCallback {
    void onAddPictureBtnClick(PostContent postContent);

    void onPicturePreview(PostContent postContent, AlbumFile albumFile);
}
