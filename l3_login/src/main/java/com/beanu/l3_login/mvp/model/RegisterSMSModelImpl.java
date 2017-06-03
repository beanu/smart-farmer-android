package com.beanu.l3_login.mvp.model;

import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterSMSModelImpl implements RegisterSMSContract.Model {

    @Override
    public Observable<SMSCode> sendSMSCode(String phoneNum) {

        return Observable.create(new ObservableOnSubscribe<SMSCode>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<SMSCode> e) throws Exception {
                SMSCode smsCode = new SMSCode();
                smsCode.setCode("666666");

                e.onNext(smsCode);
                e.onComplete();
            }
        });

    }
}