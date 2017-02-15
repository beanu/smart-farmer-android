package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.utils.StringUtils;
import com.beanu.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterSMSPresenterImpl extends RegisterSMSContract.Presenter {

    private String serverCode;

    @Override
    public void sendSMSCode(String phoneNum) {

        if (StringUtils.isPhoneFormat(phoneNum)) {

            mView.verifyPhone(true);

            mRxManage.add(mModel.sendSMSCode(phoneNum).subscribe(new Subscriber<SMSCode>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(SMSCode smsCode) {
                    serverCode = smsCode.getCode();
                }
            }));

        } else {
            mView.verifyPhone(false);
        }

    }

    @Override
    public void verifyCode(String code) {
        if (code.equals(serverCode)) {
            mView.verifyCode(true);
        } else {
            mView.verifyCode(false);
        }
    }
}