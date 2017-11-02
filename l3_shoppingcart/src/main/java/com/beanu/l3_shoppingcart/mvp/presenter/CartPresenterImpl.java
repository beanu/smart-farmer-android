package com.beanu.l3_shoppingcart.mvp.presenter;

import android.text.TextUtils;

import com.beanu.l3_shoppingcart.adapter.CartViewBinder;
import com.beanu.l3_shoppingcart.model.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.contract.CartContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 购物车 业务层
 * Created by Beanu on 2017/03/10
 */

public class CartPresenterImpl extends CartContract.Presenter implements CartViewBinder.OnShoppingCartListener {

    private final ArrayList<CartItem> mProductList = new ArrayList<>();//购物车商品列表

    private boolean deleteMode = false;//是否是删除模式
    private boolean deleteModeAllChecked = false;//删除模式  是否全部选择

    private double priceSum;//总价
    private int goodsCount;//总数量
    private boolean allChecked;//是否被全选了


    @Override
    public void onStart() {
    }

    @Override
    public void requestCartList() {

        mModel.requestCartList().subscribe(new Observer<List<CartItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull List<CartItem> cartItems) {
                mProductList.clear();
                mProductList.addAll(cartItems);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mView.requestCartListSuccess();
            }
        });
    }

    @Override
    public void selectAllGoods(boolean isSelected) {

        for (CartItem cartItem : mProductList) {
            if (deleteMode) {
                cartItem.setDelete_checked(isSelected);
            } else {
                cartItem.setSelect(isSelected ? 1 : 0);
            }
        }
        if (mProductList.size() > 0 && !deleteMode) {
            changed();
            //存到服务器
            mModel.updateAllCartShop(isSelected ? 1 : 0).subscribe(new Observer<Void>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mRxManage.add(d);
                }

                @Override
                public void onNext(@NonNull Void aVoid) {

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

    @Override
    public void removeGoods() {

        String cartIds = "";
        Iterator<CartItem> iterator = mProductList.iterator();

        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.isDelete_checked()) {
                cartIds += cartItem.getId() + ",";
                iterator.remove();
            }
        }

        //存到服务器
        if (!TextUtils.isEmpty(cartIds)) {

            mModel.deleteCartShop(cartIds).subscribe(new Observer<Void>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    mRxManage.add(d);
                }

                @Override
                public void onNext(@NonNull Void aVoid) {

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

    @Override
    public void updateCartShop(CartItem cartItem) {

        mModel.updateCartShop(cartItem).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
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
//            mRxManage.add(mModel.updateCartShop(mProductList).subscribe(new Subscriber<String>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onNext(String s) {
//
//                }
//            }));
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

                if (cartItem.isSelect() == 1) {
                    priceSum += cartItem.getPrice() * cartItem.getNum();
                    goodsCount += cartItem.getNum();
                } else {
                    allChecked = false;
                }
            }
        }
    }

    public ArrayList<CartItem> getProductList() {
        return mProductList;
    }

    public double getPriceSum() {
        return priceSum;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }
}