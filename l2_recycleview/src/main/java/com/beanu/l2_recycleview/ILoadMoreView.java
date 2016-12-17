package com.beanu.l2_recycleview;

import com.beanu.arad.base.BaseView;

import java.util.List;

/**
 * Created by lizhihua on 2016/12/8.
 * 通用加载更多 View
 */

public interface ILoadMoreView<B> extends BaseView {
    void setList(List<B> beans);
}
