package com.beanu.l3_login.mvp.model;

import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterContract;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterModelImpl implements RegisterContract.Model {

    @Override
    public Observable<String> uploadAvatar(String imgPath) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("http://p2.wmpic.me/article/2015/04/15/1429062874_HiUlpSXT.jpeg");
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Void> register(String phone, String password, String yzm, String nickname, String avatarUrl) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                //TODO 空指针
//                e.onNext(null);
                e.onComplete();
            }
        });
    }

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