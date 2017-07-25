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
import com.beanu.l2_recycleview.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 最简的recycle view,上拉 下拉
 * Created by Beanu on 2017/1/6.
 */

public abstract class SimplestRecycleViewActivity<T extends ABSLoadMorePresenter, E extends ILoadMoreModel> extends ToolBarActivity<T, E> {

    protected RecyclerView mRecycleView;
    protected PtrClassicFrameLayout mPtrFrame;
    protected RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        //初始化view
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        //定义recycle view 样式
        mAdapter = initBaseAdapter();

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);

        //上拉监听
        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager, mPresenter) {
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


    protected int getLayoutResId() {
        return R.layout.activity_recycleview_simplest;
    }


    public abstract RecyclerView.Adapter initBaseAdapter();

    public void loadDataComplete() {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
