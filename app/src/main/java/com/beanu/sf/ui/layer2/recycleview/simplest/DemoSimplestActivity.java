package com.beanu.sf.ui.layer2.recycleview.simplest;

import android.support.v7.widget.RecyclerView;

import com.beanu.arad.http.IPageModel;
import com.beanu.l2_recyclerview.SimplestListActivity;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreContract;
import com.beanu.sf.ui.layer2.recycleview.support.FakeLoader;
import com.beanu.sf.ui.layer2.recycleview.support.News;
import com.beanu.sf.ui.layer2.recycleview.support.NewsViewBinder;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 最简洁的 recycle view
 * Created by Beanu on 2017/1/6.
 */

public class DemoSimplestActivity extends SimplestListActivity<News> implements DemoHeaderLoadMoreContract.View {

    private final Items items = new Items();

    @Override
    public void loadDataComplete(List<News> beans) {
        items.clear();
        items.addAll(mPresenter.getList());
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public Observable<? extends IPageModel<News>> loadData(Map<String, Object> params, int pageIndex) {
        return FakeLoader.loadNewsList(pageIndex);
    }

    @Override
    protected RecyclerView.Adapter<?> provideAdapter() {
        MultiTypeAdapter adapter = new MultiTypeAdapter(items);
        adapter.register(News.class, new NewsViewBinder());
        return adapter;
    }
}