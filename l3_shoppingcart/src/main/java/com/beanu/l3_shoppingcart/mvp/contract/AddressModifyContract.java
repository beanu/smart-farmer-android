package com.beanu.l3_shoppingcart.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;

import java.util.Map;

import io.reactivex.Observable;


/**
 * 地址维护
 * Created by Beanu on 2017/5/11.
 */

public interface AddressModifyContract {

    public interface View extends BaseView {
        public void addOrUpdateSuccess();

        public void addOrUpdateFailed(String errorMessage);

    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 添加或更新 地址
         */
        public abstract void addOrUpdateAddress(Map<String, String> params);
    }

    public interface Model extends BaseModel {
        Observable<String> addOrUpdateAddress(Map<String, String> params);
    }

}