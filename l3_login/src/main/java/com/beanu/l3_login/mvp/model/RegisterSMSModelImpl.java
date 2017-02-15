package com.beanu.l3_login.mvp.model;

import com.beanu.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import rx.Observable;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterSMSModelImpl implements RegisterSMSContract.Model {

    @Override
    public Observable<SMSCode> sendSMSCode(String phoneNum) {
        return null;
    }
}