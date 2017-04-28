package com.beanu.sf.ui.layer2.recycleview.loadmore;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.l2_recycleview.R;
import com.beanu.sf.ui.layer2.recycleview.support.DemoLoadMoreAdapter;
import com.beanu.sf.ui.layer2.recycleview.support.News;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DemoLoadMoreActivity extends ToolBarActivity<DemoLoadMorePresenterImpl, DemoLoadMoreModelImpl> implements DemoLoadMoreContract.View {


    RecyclerView mRecycleView;
    PtrClassicFrameLayout mPtrFrame;
    DemoLoadMoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_load_more);

        //初始化view
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        //定义recycle view 样式
        mAdapter = new DemoLoadMoreAdapter(this, mPresenter.getList(), mPresenter);
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

        mPresenter.loadDataFirst();
    }

    @Override
    public void loadDataComplete(List<News> beans) {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
