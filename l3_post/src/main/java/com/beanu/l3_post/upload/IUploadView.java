package com.beanu.l3_post.upload;

import android.support.annotation.FloatRange;

import com.beanu.arad.base.BaseView;
import com.beanu.l3_post.upload.manager.UploadResponse;

import java.util.List;

/**
 * MVP 上传 View
 * @author lizhi
 * @param <T> 上传数据类型，需覆写 hashCode() 方法
 * @param <R> 服务器返回数据类型
 */
public interface IUploadView<T, R> extends BaseView {

    /**
     * 每个数据在上传之前都会回调该方法
     * @param t 上传数据
     */
    void beforeUpload(T t);

    /**
     * 上传进度
     * @param t 上传数据
     * @param percent 百分比 0..1
     */
    void onUploadInProgress(T t, @FloatRange(from = 0, to = 1) double percent);

    /**
     * 上传失败
     * @param t 上传数据
     * @param throwable 导致该资源上传失败的异常
     */
    void onUploadFailed(T t, Throwable throwable);

    /**
     * 上传成功
     * @param t 上传数据
     * @param r 服务器返回数据
     */
    void onUploadSuccess(T t, R r);

    /**
     * 上传任务结束
     * @param completeList 已上传成功的任务
     * @param failedList 上传失败的任务
     */
    void onUploadComplete(List<UploadResponse<T, R>> completeList, List<T> failedList);
}
