package com.beanu.l3_post.upload.manager;

/**
 * Created by lizhi on 2017/9/15.
 * 上传结果
 */

public class UploadResponse<T, R> {
    /**
     * 上传数据
     */
    public T source;

    /**
     * 返回结果
     */
    public R result;

    /**
     * 状态
     */
    @UploadStatus
    public int status;

    /**
     * 错误
     */
    public Throwable error;

    UploadResponse(T source, R result, int status, Throwable throwable) {
        this.source = source;
        this.result = result;
        this.status = status;
        this.error = throwable;
    }

    UploadResponse() {
    }

    @Override
    public String toString() {
        return "UploadResponse{" +
                "source=" + source +
                ", result=" + result +
                ", status=" + status +
                '}';
    }
}