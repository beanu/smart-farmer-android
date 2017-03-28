package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_login.model.bean.SMSCode;

import rx.Observable;

/**
 * 注册 契约类
 * Created by Beanu on 2017/2/13.
 */

public interface RegisterContract {

    interface View extends BaseView {

        void registerSuccess();

        void registerFail(String msg);

        void obtainSMS(String smsCode);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void uploadAvatar(String imgPath);

        public abstract void register(String phone, String password, String yzm, String nickname);

        public abstract void sendSMSCode(String phoneNum);

    }

    interface Model extends BaseModel {

        Observable<String> uploadAvatar(String imgPath);

        Observable<Void> register(String phone, String password, String yzm, String nickname, String avatarUrl);

        Observable<SMSCode> sendSMSCode(String phoneNum);

    }


}