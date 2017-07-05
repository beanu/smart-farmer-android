package com.beanu.l3_shoppingcart.mvp.presenter;

import com.beanu.l3_shoppingcart.mvp.contract.AddressModifyContract;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/05/11
 */

public class AddressModifyPresenterImpl extends AddressModifyContract.Presenter {

    @Override
    public void addOrUpdateAddress(Map<String, String> params) {

        mView.showProgress();

        mModel.addOrUpdateAddress(params).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.hideProgress();
                mView.addOrUpdateFailed(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
                mView.addOrUpdateSuccess();
            }
        });

    }

}