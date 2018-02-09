package com.beanu.l3_post.mvp.contract;


import com.beanu.l3_post.upload.IUploadModel;
import com.beanu.l3_post.upload.IUploadView;
import com.beanu.l3_post.upload.UploadPresenter;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.AlbumFile;

/**
 * Created by Beanu on 2018/1/17.
 */

public interface PostContract {

    interface View extends IUploadView<AlbumFile, JsonObject> {
    }

    abstract class Presenter extends UploadPresenter<AlbumFile, JsonObject, View, Model> {

        public abstract void postContent(String fid);

    }

    interface Model extends IUploadModel<AlbumFile, JsonObject> {

    }


}