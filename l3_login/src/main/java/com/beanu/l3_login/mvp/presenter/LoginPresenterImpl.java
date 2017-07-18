package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.ConvertUtils;
import com.beanu.arad.utils.EncryptUtils;
import com.beanu.l3_common.model.bean.EventModel;
import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_common.util.AppHolder;
import com.beanu.l3_common.util.Constants;
import com.beanu.l3_login.mvp.contract.LoginContract;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by Beanu on 2017/02/13
 */

public class LoginPresenterImpl extends LoginContract.Presenter {

    @Override
    public void login(final String account, final String password, final String loginType, String token, int sex, String icon, String nickName) {

        mModel.httpLogin(account, password, loginType, token, sex, icon, nickName).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull User user) {
                AppHolder.getInstance().setUser(user);

                //保存到本地
                if ("0".equals(loginType)) {
                    Arad.preferences.putString(Constants.P_ACCOUNT, account);
                    Arad.preferences.putString(Constants.P_PWD, EncryptUtils.encryptDES2HexString(ConvertUtils.hexString2Bytes(password), Constants.DES_KEY));
                }
                Arad.preferences.putString(Constants.P_LOGIN_TYPE, loginType);
                Arad.preferences.putString(Constants.P_User_Id, user.getId());
                Arad.preferences.putBoolean(Constants.P_ISFIRSTLOAD, false);
                Arad.preferences.flush();


                //通知登录成功
                Arad.bus.post(new EventModel.LoginEvent(user));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.loginFailed(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.loginSuccess();

            }
        });
    }
}