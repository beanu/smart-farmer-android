package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.error.AradException;
import com.beanu.l3_login.model.bean.SMSCode;
import com.beanu.l3_login.mvp.contract.RegisterContract;

import rx.Subscriber;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterPresenterImpl extends RegisterContract.Presenter {

    private String avatarPath;

    @Override
    public void uploadAvatar(String imgPath) {
        mRxManage.add(mModel.uploadAvatar(imgPath)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        avatarPath = s;
                    }
                })
        );
    }

    @Override
    public void register(String phone, String password, String yzm, String nickname) {
        mView.showProgress();
        mRxManage.add(
                mModel.register(phone, password, yzm, nickname, avatarPath)
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                                mView.hideProgress();
                                mView.registerSuccess();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.hideProgress();
                                if (e instanceof AradException) {
                                    mView.registerFail(e.getMessage());
                                } else {
                                    mView.registerFail("操作失败，请重试");
                                }
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        }));
    }

    @Override
    public void sendSMSCode(String phoneNum) {
        mRxManage.add(mModel.sendSMSCode(phoneNum).subscribe(new Subscriber<SMSCode>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(SMSCode smsCode) {
                mView.obtainSMS(smsCode.getCode());
            }
        }));
    }

}
