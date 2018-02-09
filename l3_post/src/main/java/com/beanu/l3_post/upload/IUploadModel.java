package com.beanu.l3_post.upload;

import android.support.annotation.NonNull;

import com.beanu.arad.base.BaseModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author lizhi
 * MVP 上传 Model
 * @param <T> 上传数据类型，需覆写 hashCode() 方法
 * @param <R> 服务器返回数据类型
 */

public interface IUploadModel<T, R> extends BaseModel {
    /**
     * 在进行上传任务前会回调该方法，返回的参数会传给所有上传任务
     * @return 上传所需参数，如七牛的token之类，如果没有，需返回一个 空Map
     */
    @NonNull
    Observable<Map<String, Object>> getPreUploadParams();

    /**
     * 具体的上传方法，每个任务都会通过此方法上传
     * @param t 上传数据
     * @param params {@link #getPreUploadParams()} 获取的参数
     * @param listener 进度回调
     * @return 服务器返回数据
     */
    @NonNull
    Observable<R> onUpload(T t, Map<String, Object> params, IProgressListener<T> listener);
}
