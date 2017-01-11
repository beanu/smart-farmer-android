package com.beanu.l2_recycleview.demo.loadmore_header;


import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreView;
import com.beanu.arad.support.recyclerview.loadmore.LoadMorePresenterImpl;
import com.beanu.l2_recycleview.demo.loadmore.DemoLoadMoreContract;
import com.beanu.l2_recycleview.demo.support.IndexImage;
import com.beanu.l2_recycleview.demo.support.News;

import java.util.List;

/**
 * Created by Beanu on 2017/1/6.
 */

public interface DemoHeaderLoadMoreContract {

    public interface View extends ILoadMoreView<News> {
    }

    public abstract class Presenter extends LoadMorePresenterImpl<News, DemoLoadMoreContract.View, DemoLoadMoreContract.Model> {

        public abstract List<IndexImage> getTopImageList();

    }

    public interface Model extends ILoadMoreModel<News> {
    }
}
