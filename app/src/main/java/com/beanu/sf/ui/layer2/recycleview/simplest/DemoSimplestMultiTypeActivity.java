package com.beanu.sf.ui.layer2.recycleview.simplest;

import com.beanu.l2_recyclerview.SimplestMultiTypeLoadMoreActivity;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreContract;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreModelImpl;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMorePresenterImpl;
import com.beanu.sf.ui.layer2.recycleview.support.News;
import com.beanu.sf.ui.layer2.recycleview.support.NewsViewBinder;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 最简洁的 recycle view
 * Created by Beanu on 2017/1/6.
 */

public class DemoSimplestMultiTypeActivity extends SimplestMultiTypeLoadMoreActivity<DemoHeaderLoadMorePresenterImpl, DemoHeaderLoadMoreModelImpl> implements DemoHeaderLoadMoreContract.View {

    Items mItems = new Items();

    @Override
    public void loadDataComplete(List<News> beans) {
        mItems.addAll(beans);

        super.loadDataComplete();
    }

    @Override
    public MultiTypeAdapter initBaseApater() {
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(mItems);
        multiTypeAdapter.register(News.class, new NewsViewBinder());
        return multiTypeAdapter;
    }
}
