package com.beanu.l3_login.mvp.model;

import com.beanu.l3_login.mvp.contract.RegisterContract;

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
    public Observable<String> register(String phone, String password, String yzm, String nickname, String avatarUrl) {

//        return API.getInstance(ApiLoginService.class).user_register(phone, password, yzm)
//                .compose(RxHelper.<String>handleResult());

        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("");
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<String> sendSMSCode(String phoneNum) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("666666");
                e.onComplete();
            }
        });
    }
}