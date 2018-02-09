package com.beanu.l3_post.upload;

/**
 * @author lizhi
 * @date 2017/9/13
 * 进度监听
 */

@SuppressWarnings("unused")
@FunctionalInterface
public interface IProgressListener<T> {
    /**
     * 上传百分比回调
     * @param t 上传数据
     * @param percent 百分比 0..1
     */
    void progress(T t, double percent);
}
