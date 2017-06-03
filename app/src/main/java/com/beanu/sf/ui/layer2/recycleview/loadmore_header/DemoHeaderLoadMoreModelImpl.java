package com.beanu.sf.ui.layer2.recycleview.loadmore_header;

import com.beanu.arad.http.IPageModel;
import com.beanu.sf.ui.layer2.recycleview.support.FakeLoader;
import com.beanu.sf.ui.layer2.recycleview.support.News;

import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2016/12/20
 */

public class DemoHeaderLoadMoreModelImpl implements DemoHeaderLoadMoreContract.Model {

    @Override
    public Observable<IPageModel<News>> loadData(Map<String, Object> params, int pageIndex) {
        return FakeLoader.loadNewsList(pageIndex);
    }
}