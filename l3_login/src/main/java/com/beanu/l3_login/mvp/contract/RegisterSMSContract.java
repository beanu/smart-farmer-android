package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_login.model.bean.SMSCode;

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
        public void wrongPhoneFormat();

        /**
         * 请求验证码是否正确
         */
        public void requestSMSCode(boolean correct);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void sendSMSCode(String phoneNum);

    }

    public interface Model extends BaseModel {
        Observable<SMSCode> sendSMSCode(String phoneNum);
    }


}