package com.beanu.l3_shoppingcart.mvp.model;

import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_shoppingcart.model.APICartService;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;
import com.beanu.l3_shoppingcart.mvp.contract.PlaceOrderContract;

import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2017/05/15
 */

public class PlaceOrderModelImpl implements PlaceOrderContract.Model {

    @Override
    public Observable<Map<String, String>> createBookOrder(String addressId, String cartIds) {
        return API.getInstance(APICartService.class).createBookOrder(addressId, cartIds)
                .compose(RxHelper.<Map<String, String>>handleResult());
    }

    @Override
    public Observable<AddressItem> requestMyAddressDefault() {
        return API.getInstance(APICartService.class)
                .my_address_default()
                .compose(RxHelper.<AddressItem>handleResult());
    }
}