package com.beanu.l3_login.mvp.model;

import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterContract;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterModelImpl implements RegisterContract.Model {

    @Override
    public Observable<String> uploadAvatar(String imgPath) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("http://p2.wmpic.me/article/2015/04/15/1429062874_HiUlpSXT.jpeg");
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public Observable<Void> register(String phone, String password, String yzm, String nickname, String avatarUrl) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

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