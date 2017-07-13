package com.beanu.l3_search.model;


import com.beanu.l3_common.model.HttpModel;
import com.beanu.l3_search.model.bean.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 搜索接口专用
 */
public interface APISearchService {

    //*********************************************************************************************
    //                                  搜索 相关
    //*********************************************************************************************

    @FormUrlEncoded
    @POST("searchList")
    Observable<HttpModel<SearchResult>> searchResult(@Field("subjectId") String subjectId, @Field("searchStr") String searchStr);


}