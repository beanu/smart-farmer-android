package com.beanu.l3_post.model.bean;

import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * @author lizhi
 * @date 2017/11/13.
 */
public class PostContent {
    public final boolean canDelete;
    public CharSequence content;
    public final ArrayList<AlbumFile> pictures = new ArrayList<>();

    public boolean requireUpdate = false;

    public PostContent(boolean canDelete) {
        this.canDelete = canDelete;
    }
}