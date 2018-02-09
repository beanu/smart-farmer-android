package com.beanu.l3_post.upload.manager;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 *
 * @author lizhi
 * @date 2017/9/15
 * @see UploadManager#checkHasError(List)
 */

@SuppressWarnings("unused")
public class UploadException extends RuntimeException {
    @NonNull
    private List sources;

    UploadException(@NonNull List sources) {
        super();
        this.sources = sources;
    }

    public List getSources() {
        return sources;
    }
}