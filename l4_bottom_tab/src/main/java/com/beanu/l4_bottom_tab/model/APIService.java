package com.beanu.l4_bottom_tab.model;


import com.beanu.l3_common.model.HttpModel;
import com.beanu.l3_common.model.bean.GlobalConfig;
import com.beanu.l3_common.model.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jam on 16-5-17
 * Description:
 */
public interface APIService {


    /**
     * 登录
     *
     * @param key   登录方式(tel/email)
     * @param value 手机号
     * @param pwd   密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpModel<User>> login(@Field("key") String key, @Field("val") String value, @Field("pwd") String pwd);


    /**
     * 修改用户信息
     */
    @GET("init")
    Observable<HttpModel<GlobalConfig>> getConfig();


}