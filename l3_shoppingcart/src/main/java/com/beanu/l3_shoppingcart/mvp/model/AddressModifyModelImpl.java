package com.beanu.l3_shoppingcart.mvp.model;

import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_shoppingcart.model.APICartService;
import com.beanu.l3_shoppingcart.mvp.contract.AddressModifyContract;

import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2017/05/11
 */

public class AddressModifyModelImpl implements AddressModifyContract.Model {

    @Override
    public Observable<String> addOrUpdateAddress(Map<String, String> params) {
        return API.getInstance(APICartService.class)
                .address_add(params)
                .compose(RxHelper.<String>handleResult());
    }

}