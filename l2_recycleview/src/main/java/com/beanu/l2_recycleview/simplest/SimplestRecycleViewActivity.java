package com.beanu.l2_recycleview.simplest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.arad.support.recyclerview.adapter._BaseAdapter;
import com.beanu.arad.support.recyclerview.loadmore.ABSLoadMorePresenter;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.l2_recycleview.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 最简的recycle view
 * Created by Beanu on 2017/1/6.
 */

public abstract class SimplestRecycleViewActivity<T extends ABSLoadMorePresenter, E extends ILoadMoreModel> extends ToolBarActivity<T, E> {

    RecyclerView mRecycleView;
    PtrClassicFrameLayout mPtrFrame;
    _BaseAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_simplest);

        //初始化view
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        //定义recycle view 样式
        mAdapter = initBaseApater();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        //上拉监听
        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager, mPresenter) {
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


    public abstract _BaseAdapter initBaseApater();

    public void loadDataComplete() {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
