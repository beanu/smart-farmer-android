package com.beanu.l2_recycleview;

import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.support.loadmore.ILoadMoreAdapter;

/**
 * Created by lizhihua on 2016/12/14.
 */

public abstract class BaseLoadMorePresenter<V extends ILoadMoreView, M extends ILoadMoreModel> extends BasePresenter<V, M>
        implements ILoadMoreAdapter {

    public abstract void loadDataFirst();

    public abstract void loadDataNext();
}
