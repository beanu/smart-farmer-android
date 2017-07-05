package com.beanu.l3_shoppingcart.mvp.model;

import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_shoppingcart.model.APICartService;
import com.beanu.l3_shoppingcart.model.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.contract.CartContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

/**
 * Created by Beanu on 2017/03/10
 */

public class CartModelImpl implements CartContract.Model {

    @Override
    public Observable<List<CartItem>> requestCartList() {

//        return API.getInstance(APICartService.class)
//                .shop_cart_list()
//                .compose(RxHelper.<List<CartItem>>handleResult());

        return Observable.create(new ObservableOnSubscribe<List<CartItem>>() {


            @Override
            public void subscribe(@NonNull ObservableEmitter<List<CartItem>> e) throws Exception {
                List<CartItem> list = new ArrayList<>();

                for (int i = 0; i < 10; i++) {

                    CartItem newsItem = new CartItem();
                    newsItem.setProductImg("http://img10.360buyimg.com/n1/g14/M06/12/04/rBEhVlJCkfIIAAAAAAQGliL3hoQAADiowGJ-yEABAau731.png");
                    newsItem.setNum(i + 1);
                    newsItem.setName("图书系列" + i);
                    newsItem.setPrice(i * 3.2);
                    newsItem.setPress("人民大学出版社");
                    newsItem.setSelect(1);

                    list.add(newsItem);
                }
                e.onNext(list);
                e.onComplete();
            }
        }).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Integer> updateCartShop(CartItem cartItem) {

        return API.getInstance(APICartService.class).update_shop_cart(cartItem.getId(), cartItem.getNum(), cartItem.isSelect())
                .compose(RxHelper.<Integer>handleResult());
    }

    @Override
    public Observable<Void> deleteCartShop(String cartIds) {

        return API.getInstance(APICartService.class).delete_shop_cart(cartIds)
                .compose(RxHelper.<Void>handleResult());
    }

    @Override
    public Observable<Void> updateAllCartShop(int type) {
        return API.getInstance(APICartService.class).select_shop_cart(type)
                .compose(RxHelper.<Void>handleResult());

    }
}