package com.beanu.l3_shoppingcart.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;

import java.util.Map;

import io.reactivex.Observable;


/**
 * 下订单 页面
 * Created by Beanu on 2017/5/15.
 */

public interface PlaceOrderContract {

    public interface View extends BaseView {
        public void refreshDefaultAddress(AddressItem addressItem);

        public void createBookOrderSuccess(String orderId);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void createBookOrder(String addressId, String cartIds);

        public abstract void requestMyAddressDefault();

    }

    public interface Model extends BaseModel {
        Observable<Map<String, String>> createBookOrder(String addressId, String cartIds);

        Observable<AddressItem> requestMyAddressDefault();
    }


}