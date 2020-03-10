package com.beanu.l3_login.mvp.presenter;

import com.beanu.l3_login.mvp.contract.ChangePwdContract;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/05/15
 */

public class ChangePwdPresenterImpl extends ChangePwdContract.Presenter {

    @Override
    public void sendSMSCode(String phoneNum) {

        mModel.sendSMSCode(phoneNum)
                .as(bindLifecycle())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                mView.obtainSMS(s);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void findPassword(String phoneNum, String password, String yzm) {
        mView.showProgressDialog();

        mModel.findPassword(phoneNum, password, yzm).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.hideProgressDialog();

            }

            @Override
            public void onComplete() {
                mView.hideProgressDialog();
                mView.findPwdSuccess();
            }
        });
    }
}