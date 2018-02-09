package com.beanu.l3_post.mvp.model;

import android.support.annotation.NonNull;

import com.beanu.arad.http.RxHelper;
import com.beanu.arad.utils.FileUtils;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_post.model.api.APIPostService;
import com.beanu.l3_post.mvp.contract.PostContract;
import com.beanu.l3_post.upload.IProgressListener;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Beanu on 2018/01/17
 */

public class PostModelImpl implements PostContract.Model {

    private final Map<String, Object> params = new HashMap<>();

    @NonNull
    @Override
    public Observable<Map<String, Object>> getPreUploadParams() {
        return Observable.just(params);
    }

    @NonNull
    @Override
    public Observable<JsonObject> onUpload(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {
        if (albumFile.getMediaType() == AlbumFile.TYPE_VIDEO) {
            return uploadVideo(albumFile, params, listener);
        } else {
            return uploadPicture(albumFile, params, listener);
        }
    }

    private Observable<JsonObject> uploadPicture(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {

        File file = FileUtils.getFileByPath(albumFile.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        return API.getInstance(APIPostService.class).upload_img(params, part).compose(RxHelper.handleResult());
    }

    private Observable<JsonObject> uploadVideo(AlbumFile albumFile, Map<String, Object> params, IProgressListener<AlbumFile> listener) {

        File file = FileUtils.getFileByPath(albumFile.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        return API.getInstance(APIPostService.class).upload_video(params, part).compose(RxHelper.handleResult());
    }
}