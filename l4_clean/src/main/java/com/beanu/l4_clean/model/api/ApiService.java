package com.beanu.l4_clean.model.api;


/**
 * Created by Jam on 16-5-17
 * Description:
 */
public interface ApiService {

//    /**
//     * 发送验证码
//     *
//     * @param tel        手机号
//     * @param judge_tel  是否需要判断这个号码是否存在 yes/no
//     * @param yzm_length 验证码位数,>=4  <=8
//     */
//    @GET("yzm/sendYzm")
//    Observable<BaseModel<SMSCode>> smsVCode(@HeaderMap Map<String, String> header, @Query("tel") String tel, @Query("judge_tel") String judge_tel, @Query("yzm_length") String yzm_length);
//
//    /**
//     * 注册用户
//     *
//     * @param header 头部信息
//     * @param key    注册方式(tel/email)
//     * @param val    手机号
//     * @param pwd    密码
//     */
//    @FormUrlEncoded
//    @POST("user/regist")
//    Observable<BaseModel<User>> user_register(@HeaderMap Map<String, String> header, @Field("key") String key, @Field("val") String val, @Field("pwd") String pwd, @Field("yzm") String yzm, @Field("nickname") String nickname, @Field("url") String url);
//
//    /**
//     * 登录
//     *
//     * @param key   登录方式(tel/email)
//     * @param value 手机号
//     * @param pwd   密码
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("user/login")
//    Observable<BaseModel<User>> user_login(@HeaderMap Map<String, String> header, @Field("key") String key, @Field("val") String value, @Field("pwd") String pwd);
//
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





