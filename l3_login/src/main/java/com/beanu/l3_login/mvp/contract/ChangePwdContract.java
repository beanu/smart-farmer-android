package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;

import io.reactivex.Observable;


/**
 * 更改密码
 * Created by Beanu on 2017/5/15.
 */

public interface ChangePwdContract {

    public interface View extends BaseView {

        public void obtainSMS(String smsCode);

        public void findPwdSuccess();
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void sendSMSCode(String phoneNum);

        public abstract void findPassword(String phoneNum, String password, String yzm);

    }

    public interface Model extends BaseModel {
        Observable<String> sendSMSCode(String phoneNum);

        Observable<String> findPassword(String phoneNum, String password, String yzm);

    }


}