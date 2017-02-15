package com.beanu.l3_login.mvp.model;

import com.beanu.bean.User;
import com.beanu.l3_login.mvp.contract.LoginContract;

import rx.Observable;

/**
 * Created by Beanu on 2017/02/13
 */

public class LoginModelImpl implements LoginContract.Model {

    @Override
    public Observable<User> httpLogin() {

        return null;
    }
}