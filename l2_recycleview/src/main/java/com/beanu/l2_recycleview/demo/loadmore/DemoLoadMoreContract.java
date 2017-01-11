package com.beanu.l2_recycleview.demo.loadmore;

import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreView;
import com.beanu.arad.support.recyclerview.loadmore.LoadMorePresenterImpl;
import com.beanu.l2_recycleview.demo.support.News;

/**
 * 跑腿页面MVP契约类
 * Created by Beanu on 2016/12/20.
 */

public interface DemoLoadMoreContract {

    public interface View extends ILoadMoreView<News> {
    }

    public abstract class Presenter extends LoadMorePresenterImpl<News, View, Model> {

    }

    public interface Model extends ILoadMoreModel<News> {
    }

}