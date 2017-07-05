package com.beanu.l3_shoppingcart.mvp.presenter;

import com.beanu.l3_shoppingcart.model.bean.AddressItem;
import com.beanu.l3_shoppingcart.mvp.contract.AddressContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/05/11
 */

public class AddressPresenterImpl extends AddressContract.Presenter {

    private List<AddressItem> mAddressItemList;

    @Override
    public void onStart() {
        mAddressItemList = new ArrayList<>();
    }

    @Override
    public void addressList() {

        mModel.addressList().subscribe(new Observer<List<AddressItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull List<AddressItem> addressItems) {
                mAddressItemList.clear();
                mAddressItemList.addAll(addressItems);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mView.refreshAddressList();

            }
        });
    }

    @Override
    public void addressDelete(final int position) {
        if (mAddressItemList.size() > 0) {
            String addressId = mAddressItemList.get(position).getId();
            mModel.addressDelete(addressId).subscribe(new Observer<String>() {

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    mAddressItemList.remove(position);
                    mView.refreshDeleteAddress(position);
                }

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mRxManage.add(d);
                }

                @Override
                public void onNext(String s) {

                }
            });
        }
    }

    @Override
    public void addressDefault(final int position) {
        String addressId = mAddressItemList.get(position).getId();

        mModel.addressDefault(addressId).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                for (AddressItem item : mAddressItemList) {
                    item.setIs_default(0);
                }
                mAddressItemList.get(position).setIs_default(1);

                mView.refreshDefaultAddress(position);
            }
        });
    }

    public List<AddressItem> getAddressItemList() {
        return mAddressItemList;
    }
}