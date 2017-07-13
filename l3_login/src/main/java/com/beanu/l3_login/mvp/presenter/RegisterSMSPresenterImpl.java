package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.utils.RegexUtils;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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

        if (RegexUtils.isMobileSimple(phoneNum)) {

            mModel.sendSMSCode(phoneNum).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mRxManage.add(d);
                }

                @Override
                public void onNext(@NonNull String smsCode) {
                    mVerificationCode = smsCode;
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mView.requestSMSCode(false);

                }

                @Override
                public void onComplete() {
                    mView.requestSMSCode(true);
                }
            });

        } else {
            mView.wrongPhoneFormat();
        }

    }


}