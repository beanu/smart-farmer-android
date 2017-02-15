package com.beanu.l3_login.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;

/**
 * 注册 契约类
 * Created by Beanu on 2017/2/13.
 */

public interface RegisterContract {

    public interface View extends BaseView {
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
    }

    public interface Model extends BaseModel {
    }


}