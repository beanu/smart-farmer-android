package com.beanu.l4_clean.model.api;

import com.beanu.arad.Arad;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂类
 * Created by Beanu on 15/12/29.
 */
public class APIFactory {

    private static ApiService apiServer;
    protected static final Object monitor = new Object();

    public static ApiService getApiInstance() {
        synchronized (monitor) {
            if (apiServer == null) {
                apiServer = APIRetrofit.getDefault();
            }
            return apiServer;
        }
    }

    private static class SingletonHolder {
        private static APIFactory instance = new APIFactory();
    }

    public static APIFactory getInstance() {
        return SingletonHolder.instance;
    }

    public Map<String, String> createHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("uuid", Arad.app.deviceInfo.getDeviceID());
        header.put("user_id", "1");
        header.put("sign", "");
        header.put("timestamp", System.currentTimeMillis() + "");
        header.put("phone_type", Arad.app.deviceInfo.getDeviceMode());
        header.put("version", Arad.app.deviceInfo.getVersionName());
        return header;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 接口实现
    ///////////////////////////////////////////////////////////////////////////

//    public Observable<SMSCode> sendSMSCode(String tel, String judge_tel) {
//        return APIFactory
//                .getApiInstance()
//                .smsVCode(createHeader(), tel, judge_tel, "6")
//                .compose(RxHelper.<SMSCode>handleResult());
//    }
//
//    public Observable<User> register(String phone, String password, String yzm, String nickname, String avatar) {
//        return APIFactory.getApiInstance().user_register(createHeader(), "tel", phone, password, yzm, nickname, avatar)
//                .compose(RxHelper.<User>handleResult());
//    }
//
//    public Observable<User> login(String phone, String password) {
//        return APIFactory.getApiInstance().user_login(createHeader(), "tel", phone, password)
//                .compose(RxHelper.<User>handleResult());
//    }
//
//    public Observable<SimpleModel> uploadAvatar(RequestBody file) {
//        return APIFactory.getApiInstance().uploadAvatar(createHeader(), file)
//                .compose(RxHelper.<SimpleModel>handleResult());
//    }
//
//
//    public Observable<SimpleModel> modifyUserInfo(Map<String, String> param) {
//        return APIFactory.getApiInstance().modifyUserInfo(createHeader(), param)
//                .compose(RxHelper.<SimpleModel>handleResult());
//    }


}