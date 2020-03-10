package com.beanu.l3_login.mvp.presenter;

import com.beanu.arad.error.AradException;
import com.beanu.l3_login.mvp.contract.RegisterContract;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/02/13
 */

public class RegisterPresenterImpl extends RegisterContract.Presenter {

    private String avatarPath;

    @Override
    public void uploadAvatar(String imgPath) {
        mModel.uploadAvatar(imgPath)
                .as(bindLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        avatarPath = s;

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
    public void register(String phone, String password, String yzm, String nickname) {
        mView.showProgressDialog();
        mModel.register(phone, password, yzm, nickname, avatarPath)
                .as(bindLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        mRxManage.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String aVoid) {
                        //TODO not null
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.hideProgressDialog();
                        if (e instanceof AradException) {
                            mView.registerFail(e.getMessage());
                        } else {
                            mView.registerFail("操作失败，请重试");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideProgressDialog();
                        mView.registerSuccess();
                    }
                });
    }

    @Override
    public void sendSMSCode(String phoneNum) {


        mModel.sendSMSCode(phoneNum)
                .as(bindLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        mRxManage.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String smsCode) {
                        mView.obtainSMS(smsCode);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
