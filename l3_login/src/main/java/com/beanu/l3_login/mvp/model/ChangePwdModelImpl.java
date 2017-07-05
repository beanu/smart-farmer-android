package com.beanu.l3_login.mvp.model;

import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_login.model.ApiLoginService;
import com.beanu.l3_login.mvp.contract.ChangePwdContract;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2017/05/15
 */

public class ChangePwdModelImpl implements ChangePwdContract.Model {

    @Override
    public Observable<String> sendSMSCode(String phoneNum) {
        return API.getInstance(ApiLoginService.class).smsVCode(phoneNum, "1")
                .compose(RxHelper.<String>handleResult());
    }

    @Override
    public Observable<String> findPassword(String phoneNum, String password, String yzm) {
        return API.getInstance(ApiLoginService.class).change_pwd(phoneNum, password, yzm)
                .compose(RxHelper.<String>handleResult());
    }
}