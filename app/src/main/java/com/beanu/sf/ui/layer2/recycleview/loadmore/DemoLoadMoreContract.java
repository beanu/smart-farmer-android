package com.beanu.sf.ui.layer2.recycleview.loadmore;

import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreView;
import com.beanu.arad.support.recyclerview.loadmore.LoadMorePresenterImpl;
import com.beanu.sf.ui.layer2.recycleview.support.News;

/**
 * 跑腿页面MVP契约类
 * Created by Beanu on 2016/12/20.
 */

public interface DemoLoadMoreContract {

    interface View extends ILoadMoreView<News> {
    }

    abstract class Presenter extends LoadMorePresenterImpl<News, View, Model> {

    }

    interface Model extends ILoadMoreModel<News> {
    }

}