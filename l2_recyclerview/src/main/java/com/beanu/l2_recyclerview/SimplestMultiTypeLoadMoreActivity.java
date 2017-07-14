package com.beanu.l2_recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.arad.support.recyclerview.loadmore.ABSLoadMorePresenter;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.l2_recyclerview.loadmore.BaseLoadMoreMultiTypeAdapter;
import com.beanu.l2_recyclerview.loadmore.LoadMoreFooterViewBinder;
import com.beanu.l2_recycleview.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 最简的recycle view,适合列表中多种类型
 * Created by Beanu on 2017/7/13.
 */

public abstract class SimplestMultiTypeLoadMoreActivity<T extends ABSLoadMorePresenter, E extends ILoadMoreModel> extends ToolBarActivity<T, E> {

    protected RecyclerView mRecycleView;
    protected PtrClassicFrameLayout mPtrFrame;
    protected BaseLoadMoreMultiTypeAdapter mAdapter;
    protected MultiTypeAdapter mMultiTypeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_simplest);

        //初始化view
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        //定义recycle view 样式
        mMultiTypeAdapter = initBaseApater();
        mAdapter = new BaseLoadMoreMultiTypeAdapter(mMultiTypeAdapter, mPresenter);
        mAdapter.setOnFooterListener(new LoadMoreFooterViewBinder.OnFooterListener() {
            @Override
            public void onRetry() {
                //非第一页数据，如果加载出错，点击会重新加载数据
                mPresenter.loadDataNext();
            }
        });

        mRecycleView.setLayoutManager(getLayoutManager());
        mRecycleView.setAdapter(mAdapter);

        //上拉监听
        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(getLayoutManager(), mPresenter) {
            @Override
            public void onLoadMore() {
                mPresenter.loadDataNext();
            }
        });

        //下拉刷新监听
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.loadDataFirst();
            }
        });

        //第一次加载数据
        mPresenter.loadDataFirst();
    }

    @NonNull
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    public abstract MultiTypeAdapter initBaseApater();

    protected void loadDataComplete() {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
