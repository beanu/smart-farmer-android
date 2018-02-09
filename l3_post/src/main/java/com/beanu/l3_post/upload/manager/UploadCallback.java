package com.beanu.l3_post.upload.manager;

import android.support.annotation.NonNull;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by lizhi on 2017/9/15.
 * 上传过程中的回调
 */

public interface UploadCallback<T, S> {

    /**
     * 上传所有数据之前获取上传所需得参数，比如七牛上传要使用的Token
     * @return 参数
     */
    @NonNull
    Observable<Map<String, Object>> getPreUploadParams();

    /**
     * 每个任务上传之前都会执行这个方法
     * @param t 上传的数据
     */
    void doOnPreUpload(@NonNull T t);

    /**
     * 上传方法
     * @param t 上传的数据
     * @param params {@link #getPreUploadParams()} 发射的数据
     * @return Observable
     */
    @NonNull
    Observable<S> onUpload(T t, Map<String, Object> params);
}