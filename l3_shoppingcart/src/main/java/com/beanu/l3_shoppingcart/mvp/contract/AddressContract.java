package com.beanu.l3_shoppingcart.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;

import java.util.List;

import io.reactivex.Observable;


/**
 * 地址 契约类
 * Created by Beanu on 2017/5/11.
 */

public interface AddressContract {


    public interface View extends BaseView {
        public void refreshAddressList();

        public void refreshDeleteAddress(int position);

        public void refreshDefaultAddress(int position);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {

        /**
         * 获取地址列表
         */
        public abstract void addressList();

        /**
         * 删除地址
         *
         * @param position 要删除的位置
         */
        public abstract void addressDelete(int position);

        /**
         * 地址设为默认
         *
         * @param position 要设置默认地址的位置
         */
        public abstract void addressDefault(int position);
    }

    public interface Model extends BaseModel {

        Observable<List<AddressItem>> addressList();

        Observable<String> addressDelete(String addressId);

        Observable<String> addressDefault(String addressId);

    }


}