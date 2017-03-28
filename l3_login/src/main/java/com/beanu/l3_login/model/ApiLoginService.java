package com.beanu.l3_login.model;


import com.beanu.l3_common.model.HttpModel;
import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_login.model.bean.SMSCode;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 登录 注册接口专用
 */
public interface ApiLoginService {

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @param type   类型 0注册 1找回密码
     */
    @GET("getCode")
    Observable<HttpModel<SMSCode>> smsVCode(@HeaderMap Map<String, String> header, @Query("mobile") String mobile, @Query("type") String type);

    /**
     * 注册用户
     *
     * @param header 头部信息
     */
    @FormUrlEncoded
    @POST("register")
    Observable<HttpModel<Void>> user_register(@HeaderMap Map<String, String> header, @Field("account") String account, @Field("password") String password, @Field("code") String code);

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
    Observable<HttpModel<User>> user_login(@HeaderMap Map<String, String> header, @Field("account") String account, @Field("password") String password, @Field("loginType") String loginType, @Field("token") String token);

//
//    /**
//     * 上传用户头像
//     *
//     * @param file 头像文件
//     */
//    @Multipart
//    @POST("img/uploadFirstUseFormData")
//    Observable<BaseModel<SimpleModel>> uploadAvatar(@HeaderMap Map<String, String> header, @Part("img\"; filename=\"avatar.png\"") RequestBody file);
//
//
//    /**
//     * 修改用户信息
//     */
//    @FormUrlEncoded
//    @POST("user/info_edit")
//    Observable<BaseModel<SimpleModel>> modifyUserInfo(@HeaderMap Map<String, String> header, @FieldMap Map<String, String> param);


}





