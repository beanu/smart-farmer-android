package com.beanu.l3_login.mvp.presenter;

import com.beanu.bean.User;
import com.beanu.l3_login.mvp.contract.LoginContract;

import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class LoginPresenterImpl extends LoginContract.Presenter {

    @Override
    public void login(String account, String password) {

        mRxManage.add(mModel.httpLogin().subscribe(new Subscriber<User>() {

            @Override
            public void onCompleted() {
                mView.loginSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mView.loginFailed(e.getMessage());
            }

            @Override
            public void onNext(User user) {
                //TODO 给全局user赋值

//                AppHolder.getInstance().setUser(user);

//                //保存到本地
//                Arad.preferences.putString(Constants.P_Name, user.getLogin_value());
//                Arad.preferences.putString(Constants.P_Password, password);
//                Arad.preferences.putString(Constants.P_User_Id, user.getUser_id());
//                Arad.preferences.putBoolean(Constants.P_ISFIRSTLOAD, false);
//                Arad.preferences.flush();
//

            }
        }));
    }
}