package com.beanu.l3_login.model;


import com.beanu.l3_common.model.HttpModel;
import com.beanu.l3_common.model.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 登录 注册接口专用
 */
public interface APILoginService {

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @param type   类型 0注册 1找回密码
     */
    @GET("getCode")
    Observable<HttpModel<String>> smsVCode(@Query("mobile") String mobile, @Query("type") String type);

    /**
     * 注册用户
     **/
    @FormUrlEncoded
    @POST("register")
    Observable<HttpModel<String>> user_register(@Field("account") String account, @Field("password") String password, @Field("code") String code);

    /**
     * 登录
     *
     * @param account   手机号
     * @param password  密码
     * @param loginType 登录类型 0 账号登录 1ＱＱ登录　2微信登录
     * @param token     第三方登录时必传  qq或微信 token
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<HttpModel<User>> user_login(@Field("account") String account, @Field("password") String password, @Field("loginType") String loginType, @Field("token") String token);


    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST("retrievePassword")
    Observable<HttpModel<String>> change_pwd(@Field("account") String phone, @Field("password") String password, @Field("code") String code);


}





