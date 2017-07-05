package com.beanu.l3_shoppingcart.mvp.model;

import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_shoppingcart.model.APICartService;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;
import com.beanu.l3_shoppingcart.mvp.contract.AddressContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2017/05/11
 */

public class AddressModelImpl implements AddressContract.Model {

    @Override
    public Observable<List<AddressItem>> addressList() {
        return API.getInstance(APICartService.class)
                .address_list()
                .compose(RxHelper.<List<AddressItem>>handleResult());
    }

    @Override
    public Observable<String> addressDelete(String addressId) {
        return API.getInstance(APICartService.class)
                .address_delete(addressId)
                .compose(RxHelper.<String>handleResult());
    }

    @Override
    public Observable<String> addressDefault(String addressId) {
        return API.getInstance(APICartService.class)
                .address_default(addressId)
                .compose(RxHelper.<String>handleResult());
    }
}