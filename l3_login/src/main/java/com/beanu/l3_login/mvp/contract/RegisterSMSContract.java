package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.bean.SMSCode;

import rx.Observable;

/**
 * 注册 获取短信验证码
 * Created by Beanu on 2017/2/13.
 */

public interface RegisterSMSContract {

    public interface View extends BaseView {
        /**
         * 验证手机号是否正确
         */
        public abstract void verifyPhone(boolean correct);

        /**
         * 验证 验证码是否正确
         */
        public abstract void verifyCode(boolean correct);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void sendSMSCode(String phoneNum);

        public abstract void verifyCode(String code);
    }

    public interface Model extends BaseModel {
        Observable<SMSCode> sendSMSCode(String phoneNum);
    }


}