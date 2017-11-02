package com.beanu.sf.ui.layer2.recycleview.loadmore_header;


import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel;
import com.beanu.arad.support.recyclerview.loadmore.ILoadMoreView;
import com.beanu.arad.support.recyclerview.loadmore.LoadMorePresenterImpl;
import com.beanu.sf.ui.layer2.recycleview.loadmore.DemoLoadMoreContract;
import com.beanu.sf.ui.layer2.recycleview.support.IndexImage;
import com.beanu.sf.ui.layer2.recycleview.support.News;

import java.util.List;

/**
 * Created by Beanu on 2017/1/6.
 */

public interface DemoHeaderLoadMoreContract {

    interface View extends ILoadMoreView<News> {
    }

    abstract class Presenter extends LoadMorePresenterImpl<News, DemoLoadMoreContract.View, DemoLoadMoreContract.Model> {

        public abstract List<IndexImage> getTopImageList();

    }

    interface Model extends ILoadMoreModel<News> {
    }
}
