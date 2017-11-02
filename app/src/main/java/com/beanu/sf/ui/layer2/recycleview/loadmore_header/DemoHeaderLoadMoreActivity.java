package com.beanu.sf.ui.layer2.recycleview.loadmore_header;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.recyclerview.adapter.EndlessRecyclerOnScrollListener;
import com.beanu.arad.support.recyclerview.adapter.LoadMoreAdapterWrapper;
import com.beanu.sf.R;
import com.beanu.sf.ui.layer2.recycleview.support.DemoHeader;
import com.beanu.sf.ui.layer2.recycleview.support.HeaderViewBinder;
import com.beanu.sf.ui.layer2.recycleview.support.News;
import com.beanu.sf.ui.layer2.recycleview.support.NewsViewBinder;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class DemoHeaderLoadMoreActivity extends ToolBarActivity<DemoHeaderLoadMorePresenterImpl, DemoHeaderLoadMoreModelImpl> implements DemoHeaderLoadMoreContract.View {

    RecyclerView mRecycleView;
    PtrClassicFrameLayout mPtrFrame;
    MultiTypeAdapter mAdapter;
    final Items items = new Items();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_header_load_more);

        mPtrFrame = findViewById(R.id.arad_content);
        mRecycleView = findViewById(R.id.recycle_view);

        items.add(new DemoHeader(mPresenter.getTopImageList()));
        items.addAll(mPresenter.getList());
        mAdapter = new MultiTypeAdapter(items);
        mAdapter.register(DemoHeader.class, new HeaderViewBinder());
        mAdapter.register(News.class, new NewsViewBinder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(new LoadMoreAdapterWrapper(this, mAdapter, mPresenter));

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
        items.clear();
        items.add(new DemoHeader(mPresenter.getTopImageList()));
        items.addAll(mPresenter.getList());
        mPtrFrame.refreshComplete();
        mAdapter.notifyDataSetChanged();
    }
}