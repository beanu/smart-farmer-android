package com.beanu.sf.ui.layer2.recycleview.simplest;

import com.beanu.arad.support.recyclerview.adapter._BaseAdapter;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreContract;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreModelImpl;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMorePresenterImpl;
import com.beanu.sf.ui.layer2.recycleview.support.DemoHeaderLoadMoreAdapter;
import com.beanu.sf.ui.layer2.recycleview.support.News;
import com.beanu.l2_recyclerview.SimplestRecycleViewActivity;

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
