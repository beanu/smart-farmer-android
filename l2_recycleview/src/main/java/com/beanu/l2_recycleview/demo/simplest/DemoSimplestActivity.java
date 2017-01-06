package com.beanu.l2_recycleview.demo.simplest;

import com.beanu.arad.support.recyclerview.adapter._BaseAdapter;
import com.beanu.l2_recycleview.demo.loadmore_header.DemoHeaderLoadMoreContract;
import com.beanu.l2_recycleview.demo.loadmore_header.DemoHeaderLoadMoreModelImpl;
import com.beanu.l2_recycleview.demo.loadmore_header.DemoHeaderLoadMorePresenterImpl;
import com.beanu.l2_recycleview.demo.support.DemoHeaderLoadMoreAdapter;
import com.beanu.l2_recycleview.demo.support.News;
import com.beanu.l2_recycleview.simplest.SimplestRecycleViewActivity;

import java.util.List;

/**
 * 最简洁的 recycle view
 * Created by Beanu on 2017/1/6.
 */

public class DemoSimplestActivity extends SimplestRecycleViewActivity<DemoHeaderLoadMorePresenterImpl, DemoHeaderLoadMoreModelImpl> implements DemoHeaderLoadMoreContract.View {

    @Override
    public _BaseAdapter initBaseApater() {
        return new DemoHeaderLoadMoreAdapter(this, mPresenter.getList(), mPresenter.getTopImageList(), mPresenter);
    }

    @Override
    public void loadDataComplete(List<News> beans) {
        loadDataComplete();
    }
}
