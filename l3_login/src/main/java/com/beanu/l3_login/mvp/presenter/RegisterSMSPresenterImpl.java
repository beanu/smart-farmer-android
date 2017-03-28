package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.utils.StringUtils;
import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterSMSPresenterImpl extends RegisterSMSContract.Presenter {

    private String mVerificationCode;

    public String getVerificationCode() {
        return mVerificationCode;
    }

    @Override
    public void sendSMSCode(String phoneNum) {

        if (StringUtils.isPhoneFormat(phoneNum)) {

            mRxManage.add(mModel.sendSMSCode(phoneNum).subscribe(new Subscriber<SMSCode>() {
                @Override
                public void onCompleted() {
                    mView.requestSMSCode(true);
                }

                @Override
                public void onError(Throwable e) {
                    mView.requestSMSCode(false);
                }

                @Override
                public void onNext(SMSCode smsCode) {
                    mVerificationCode = smsCode.getCode();
                }
            }));

        } else {
            mView.wrongPhoneFormat();
        }

    }


}