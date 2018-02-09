package com.beanu.l3_post.model.api;


import com.beanu.l3_common.model.HttpModel;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * 发布论坛
 */
public interface APIPostService {

    /**
     * 上传图片
     */
    @Multipart
    @POST("api")
    Observable<HttpModel<JsonObject>> upload_img(@PartMap Map<String, RequestBody> partMap, @Part MultipartBody.Part file);

    /**
     * 上传视频
     */
    @Multipart
    @POST("api")
    Observable<HttpModel<JsonObject>> upload_video(@PartMap Map<String, RequestBody> partMap, @Part MultipartBody.Part file);


}