package com.beanu.l3_shoppingcart.mvp.presenter;

import com.beanu.l3_shoppingcart.adapter.CartAdapter;
import com.beanu.l3_shoppingcart.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.contract.CartContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;

/**
 * 购物车 业务层
 * Created by Beanu on 2017/03/10
 */

public class CartPresenterImpl extends CartContract.Presenter implements CartAdapter.OnShoppingCartListener {

    private List<CartItem> mProductList;//购物车商品列表

    private boolean deleteMode = false;//是否是删除模式
    private boolean deleteModeAllChecked = false;//删除模式  是否全部选择

    private double priceSum;//总价
    private int goodsCount;//总数量
    private boolean allChecked;//是否被全选了


    @Override
    public void onStart() {
        mProductList = new ArrayList<>();
    }

    @Override
    public void requestCartList() {
        mRxManage.add(mModel.requestCartList().subscribe(new Subscriber<List<CartItem>>() {
            @Override
            public void onCompleted() {
                mView.requestCartListSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CartItem> products) {
                mProductList.clear();
                mProductList.addAll(products);
            }
        }));
    }

    @Override
    public void selectAllGoods(boolean isSelected) {

        for (CartItem cartItem : mProductList) {
            if (deleteMode) {
                cartItem.setDelete_checked(isSelected);
            } else {
                cartItem.setCart_checked(isSelected);
            }
        }
        if (mProductList.size() > 0 && !deleteMode) {
            changed();
        }
    }

    @Override
    public void removeGoods() {
        Iterator<CartItem> iterator = mProductList.iterator();

        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.isDelete_checked()) {
                iterator.remove();
            }
        }

    }


    @Override
    public void changed() {

        if (deleteMode) {
            changeAllCheckDelete();
            mView.updateBottomBarDelete(deleteModeAllChecked);
        } else {

            //1.计算价格和数量
            updatePriceAndAmount();

            //2.更新到view层
            mView.updateBottomBar(priceSum, goodsCount, allChecked);

            //3.更新到服务器
            mRxManage.add(mModel.uploadCardList(mProductList).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String s) {

                }
            }));
        }
    }

    public void changeAllCheckDelete() {
        deleteModeAllChecked = true;
        if (mProductList.size() > 0) {
            for (CartItem cartItem : mProductList) {

                if (!cartItem.isDelete_checked()) {
                    deleteModeAllChecked = false;
                    break;
                }
            }
        }
    }

    //统计价格和数量
    private void updatePriceAndAmount() {

        priceSum = 0;
        goodsCount = 0;
        allChecked = true;

        if (mProductList.size() > 0) {
            for (CartItem cartItem : mProductList) {

                if (cartItem.isCart_checked()) {
                    priceSum += cartItem.getPrice() * cartItem.getCart_amount();
                    goodsCount += cartItem.getCart_amount();
                } else {
                    allChecked = false;
                }
            }
        }
    }

    public List<CartItem> getProductList() {
        return mProductList;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }
}