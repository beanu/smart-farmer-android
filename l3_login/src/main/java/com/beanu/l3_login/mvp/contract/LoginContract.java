package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_common.model.bean.User;

import rx.Observable;

/**
 * 登录-契约类
 * Created by Beanu on 2017/2/13.
 */

public interface LoginContract {

    public interface View extends BaseView {

        public abstract void loginSuccess();

        public abstract void loginFailed(String error);

    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void login(String account, String password);
    }

    public interface Model extends BaseModel {
        Observable<User> httpLogin();
    }


}