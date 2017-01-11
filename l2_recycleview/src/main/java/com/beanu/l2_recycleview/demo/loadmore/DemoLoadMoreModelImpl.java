package com.beanu.l2_recycleview.demo.loadmore;

import com.beanu.arad.http.IPageModel;
import com.beanu.l2_recycleview.demo.support.FakeLoader;
import com.beanu.l2_recycleview.demo.support.News;

import java.util.Map;

import rx.Observable;

/**
 * Created by Beanu on 2016/12/20
 */

public class DemoLoadMoreModelImpl implements DemoLoadMoreContract.Model {

    @Override
    public Observable<IPageModel<News>> loadData(Map<String, Object> params, int pageIndex) {
        return FakeLoader.loadNewsList(pageIndex);
    }
}