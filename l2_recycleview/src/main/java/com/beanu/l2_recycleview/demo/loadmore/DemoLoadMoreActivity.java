package com.beanu.l2_recycleview.demo.loadmore;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.customAnim.PtrAnimFrameLayout;
import com.beanu.l2_recycleview.demo.support.DemoLoadMoreAdapter;
import com.beanu.l2_recycleview.demo.support.News;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DemoLoadMoreActivity extends ToolBarActivity<DemoLoadMorePresenterImpl, DemoLoadMoreModelImpl> implements DemoLoadMoreContract.View {


    RecyclerView mRecycleView;
    PtrAnimFrameLayout mPtrFrame;
    DemoLoadMoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_load_more);

        mPtrFrame = (PtrAnimFrameLayout) findViewById(R.id.arad_content);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);

        mAdapter = new DemoLoadMoreAdapter(this, mPresenter.getList());
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
    public void loadDataComplete(List<News> beans) {
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}
