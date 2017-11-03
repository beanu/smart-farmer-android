package com.beanu.l2_recyclerview;

import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreView;
import com.beanu.arad.support.recyclerview.loadmore.LoadMorePresenterImpl;

/**
 * @author lizhi
 * @date 2017/11/1.
 */

public abstract class SimplestListFragment<B> extends BaseListFragment<LoadMorePresenterImpl, ILoadMoreModel>
        implements ILoadMoreView<B>, ILoadMoreModel<B> {

    @Override
    protected LoadMorePresenterImpl obtainPresenter() {
        return new LoadMorePresenterImpl();
    }

    @Override
    protected ILoadMoreModel obtainModel() {
        return this;
    }
}