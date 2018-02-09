package com.beanu.l3_post.upload.manager;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lizhi on 2017/9/15.
 * 上传状态
 */

@IntDef({UploadStatus.STATUS_OK, UploadStatus.STATUS_ERROR, UploadStatus.STATUS_CANCEL})
@Retention(RetentionPolicy.SOURCE)
public @interface UploadStatus {
    /**
     * 成功
     */
    int STATUS_OK = 0;

    /**
     * 失败
     */
    int STATUS_ERROR = -1;

    /**
     * 取消
     */
    int STATUS_CANCEL = -2;
}
