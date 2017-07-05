package com.beanu.l3_shoppingcart.mvp.presenter;

import com.beanu.l3_shoppingcart.model.bean.AddressItem;
import com.beanu.l3_shoppingcart.mvp.contract.PlaceOrderContract;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/05/15
 */

public class PlaceOrderPresenterImpl extends PlaceOrderContract.Presenter {

    @Override
    public void createBookOrder(String addressId, String cartIds) {
        mView.showProgress();

        mModel.createBookOrder(addressId, cartIds).subscribe(new Observer<Map<String, String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull Map<String, String> map) {
                mView.createBookOrderSuccess(map.get("orderId"));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.hideProgress();

            }

            @Override
            public void onComplete() {
                mView.hideProgress();

            }
        });


    }

    @Override
    public void requestMyAddressDefault() {
        mModel.requestMyAddressDefault().subscribe(new Observer<AddressItem>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull AddressItem addressItem) {
                mView.refreshDefaultAddress(addressItem);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}