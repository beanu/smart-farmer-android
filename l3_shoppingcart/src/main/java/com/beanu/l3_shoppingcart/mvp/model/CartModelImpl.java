package com.beanu.l3_shoppingcart.mvp.model;

import com.beanu.l3_shoppingcart.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.contract.CartContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Beanu on 2017/03/10
 */

public class CartModelImpl implements CartContract.Model {

    @Override
    public Observable<List<CartItem>> requestCartList() {

        //TODO replace
        return Observable.create(new Observable.OnSubscribe<List<CartItem>>() {
            @Override
            public void call(Subscriber<? super List<CartItem>> subscriber) {

                List<CartItem> list = new ArrayList<>();

                for (int i = 0; i < 10; i++) {

                    CartItem newsItem = new CartItem();
                    newsItem.setFaceImgPath("http://img10.360buyimg.com/n1/g14/M06/12/04/rBEhVlJCkfIIAAAAAAQGliL3hoQAADiowGJ-yEABAau731.png");
                    newsItem.setCart_amount(i + 1);
                    newsItem.setName("图书系列" + i);
                    newsItem.setPrice(i * 3.2);
                    newsItem.setSku("人民大学出版社");
                    newsItem.setCart_checked(true);

                    list.add(newsItem);
                }
                subscriber.onNext(list);
                subscriber.onCompleted();

            }
        }).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> uploadCardList(List<CartItem> cartItemList) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("success");
                subscriber.onCompleted();

//                subscriber.onError(new NumberFormatException());
            }
        });
    }
}