package com.beanu.l3_login.mvp.model;

import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_login.mvp.contract.LoginContract;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Created by Beanu on 2017/02/13
 */

public class LoginModelImpl implements LoginContract.Model {

    @Override
    public Observable<User> httpLogin() {

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