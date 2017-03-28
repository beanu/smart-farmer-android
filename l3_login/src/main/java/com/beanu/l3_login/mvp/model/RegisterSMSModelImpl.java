package com.beanu.l3_login.mvp.model;

import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterSMSModelImpl implements RegisterSMSContract.Model {

    @Override
    public Observable<SMSCode> sendSMSCode(String phoneNum) {

        return Observable.create(new Observable.OnSubscribe<SMSCode>() {
            @Override
            public void call(Subscriber<? super SMSCode> subscriber) {

                SMSCode smsCode = new SMSCode();
                smsCode.setCode("666666");

                subscriber.onNext(smsCode);
                subscriber.onCompleted();

            }
        });

    }
}