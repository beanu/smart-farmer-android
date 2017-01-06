package com.beanu.l2_recycleview.demo.loadmore_header;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.support.DemoHeaderLoadMoreAdapter;
import com.beanu.l2_recycleview.demo.support.News;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DemoHeaderLoadMoreActivity extends ToolBarActivity<DemoHeaderLoadMorePresenterImpl, DemoHeaderLoadMoreModelImpl> implements DemoHeaderLoadMoreContract.View {

    RecyclerView mRecycleView;
    PtrClassicFrameLayout mPtrFrame;
    DemoHeaderLoadMoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_header_load_more);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        mAdapter = new DemoHeaderLoadMoreAdapter(this, mPresenter.getList(), mPresenter.getTopImageList(), mPresenter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        mPresenter.loadDataFirst();

        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager, mPresenter) {
            @Override
            public void onLoadMore() {
                mPresenter.loadDataNext();
            }
        });


        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.loadDataFirst();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.getViewPagerAutoScroll().start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.getViewPagerAutoScroll().stop();
    }

    @Override
    public void loadDataComplete(List<News> beans) {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}