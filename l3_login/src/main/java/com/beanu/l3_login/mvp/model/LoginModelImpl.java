package com.beanu.l3_login.mvp.model;

import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_login.mvp.contract.LoginContract;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class LoginModelImpl implements LoginContract.Model {

    @Override
    public Observable<User> httpLogin() {

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                User user = new User();
                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }
}