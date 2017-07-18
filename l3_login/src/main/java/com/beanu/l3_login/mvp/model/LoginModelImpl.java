package com.beanu.l3_login.mvp.model;

import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_login.mvp.contract.LoginContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by Beanu on 2017/02/13
 */

public class LoginModelImpl implements LoginContract.Model {


    @Override
    public Observable<User> httpLogin(String account, String password, String loginType, String token, int sex, String icon, String nickName) {

//        return API.getInstance(ApiLoginService.class).user_login(API.createHeader(), account, password, loginType, token, sex, icon, nickName)
//                .compose(RxHelper.<User>handleResult());
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> e) throws Exception {
                User user = new User();
                e.onNext(user);
                e.onComplete();
            }
        });
    }

}