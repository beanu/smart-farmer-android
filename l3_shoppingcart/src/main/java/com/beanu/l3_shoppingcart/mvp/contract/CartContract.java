package com.beanu.l3_shoppingcart.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_shoppingcart.bean.CartItem;

import java.util.List;

import rx.Observable;

/**
 * 购物车 MVP
 * Created by Beanu on 2017/3/10.
 */

public interface CartContract {

    public interface View extends BaseView {
        public void requestCartListSuccess();

        public void updateBottomBar(double price, int amount, boolean allChecked);

        public void updateBottomBarDelete(boolean allChecked);

    }

    public abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void requestCartList();

        public abstract void selectAllGoods(boolean isSelected);

        public abstract void removeGoods();
    }

    public interface Model extends BaseModel {

        Observable<List<CartItem>> requestCartList();

        Observable<String> uploadCardList(List<CartItem> cartItemList);
    }


}