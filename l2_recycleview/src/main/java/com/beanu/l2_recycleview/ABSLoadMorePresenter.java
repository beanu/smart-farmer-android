package com.beanu.l2_recycleview;

import android.support.v4.util.ArrayMap;

import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.support.loadmore.ILoadMoreAdapter;

import java.util.Map;

/**
 * LoadMore P层
 * Created by lizhihua on 2016/12/14.
 */

public abstract class ABSLoadMorePresenter<V extends ILoadMoreView, M extends ILoadMoreModel> extends BasePresenter<V, M>
        implements ILoadMoreAdapter {

    /**
     * 初始化Model层的数据请求参数
     * <p>
     * {@link com.beanu.l2_recycleview.ILoadMoreModel#loadData(Map, int)}
     *
     * @param params loadData 参数
     */
    public abstract void initLoadDataParams(ArrayMap<String, Object> params);

    /**
     * 第一次请求
     */
    public abstract void loadDataFirst();

    /**
     * 请求下一页数据
     */
    public abstract void loadDataNext();
}
