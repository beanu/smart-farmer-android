package com.beanu.l2_recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.base.ToolBarFragment;
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
 * Created by Beanu on 2017/1/14.
 */

public abstract class SimplestMultiTypeLoadMoreFragment<T extends ABSLoadMorePresenter, E extends ILoadMoreModel> extends ToolBarFragment<T, E> {


    protected RecyclerView mRecycleView;
    protected PtrClassicFrameLayout mPtrFrame;
    protected BaseLoadMoreMultiTypeAdapter mAdapter;
    protected MultiTypeAdapter mMultiTypeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMultiTypeAdapter = initBaseAdapter();
        mAdapter = new BaseLoadMoreMultiTypeAdapter(mMultiTypeAdapter, mPresenter);
        mAdapter.setOnFooterListener(new LoadMoreFooterViewBinder.OnFooterListener() {
            @Override
            public void onRetry() {
                //非第一页数据，如果加载出错，点击会重新加载数据
                mPresenter.loadDataNext();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化view
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_view);

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
        return new LinearLayoutManager(getActivity());
    }

    protected int getLayoutResId() {
        return R.layout.fragment_recycleview_simplest;
    }

    public abstract MultiTypeAdapter initBaseAdapter();

    public void loadDataComplete() {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }

}
