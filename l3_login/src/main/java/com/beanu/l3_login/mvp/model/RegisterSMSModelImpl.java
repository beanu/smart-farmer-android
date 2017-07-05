package com.beanu.l3_login.mvp.model;

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
    public Observable<String> sendSMSCode(String phoneNum) {

//        return API.getInstance(ApiLoginService.class).smsVCode(phoneNum, "0")
//                .compose(RxHelper.<String>handleResult());

        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("666666");
                e.onComplete();
            }
        });

    }
}