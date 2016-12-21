package com.beanu.l2_recycleview;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by lizhihua on 2016/12/14.
 * 通用加载更多 Presenter
 */

/**
 *
 * @param <B> 列表数据类型
 * @param <V> 实现了 ILoadMoreView接口 的类, 通常填写是当前 Activity (需要实现ILoadMoreView接口)
 *           或 Fragment (需要实现ILoadMoreView接口)
 * @param <M> 实现了 ILoadMoreModel 接口 的类
 */
public class LoadMorePresenterImpl<B, V extends ILoadMoreView<B>, M extends ILoadMoreModel<B>>
        extends BaseLoadMorePresenter<V, M> {

    private int mCurPage = 0;
    private PageModel<B> mPageModel;
    private List<B> mList = new ArrayList<>();

    private boolean mHasError = false;
    private boolean mIsLoading = false;

    public List<B> getList(){
        return mList;
    }

    @Override
    public boolean hasMoreResults() {
        return mPageModel == null || (mPageModel.currentPage < mPageModel.totalPage);
    }

    @Override
    public boolean hasError() {
        return mHasError;
    }

    @Override
    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void loadDataFirst() {
        mCurPage = 0;
        loadDataNext();
    }

    @Override
    public void loadDataNext() {
        ++mCurPage;
        mIsLoading = true;
        mRxManage.add(mModel.loadData(mCurPage).subscribe(new Subscriber<PageModel<B>>() {
            @Override
            public void onCompleted() {
                mIsLoading = false;
                mHasError = false;
                if (mList == null || mList.isEmpty()) {
                    mView.contentLoadingEmpty();
                } else {
                    mView.contentLoadingComplete();
                }
                mView.setList(mList);
            }

            @Override
            public void onError(Throwable e) {
                mIsLoading = false;
                mHasError = true;
                if (mList == null || mList.isEmpty()) {
                    mView.contentLoadingError();
                }
            }

            @Override
            public void onNext(PageModel<B> pageModel) {
                mPageModel = pageModel;
                if(mCurPage == 1){//第一次加载或者重新加载
                    mList.clear();
                }
                if (pageModel.dataList != null && !pageModel.dataList.isEmpty()) {
                    mList.addAll(pageModel.dataList);
                }
            }
        }));
    }
}
