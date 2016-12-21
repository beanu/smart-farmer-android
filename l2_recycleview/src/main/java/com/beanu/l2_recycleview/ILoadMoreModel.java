package com.beanu.l2_recycleview;

import com.beanu.arad.base.BaseModel;

import rx.Observable;

/**
 * Created by lizhihua on 2016/12/14.
 * 通用加载更多Model
 * 继承该接口, 添加过滤方法
 */

public interface ILoadMoreModel<B> extends BaseModel {
    Observable<PageModel<B>> loadData(int pageIndex);
}
