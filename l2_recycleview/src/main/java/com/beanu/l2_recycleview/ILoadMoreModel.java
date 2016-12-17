package com.beanu.l2_recycleview;

import com.beanu.arad.base.BaseModel;

import rx.Observable;

/**
 * Created by lizhihua on 2016/12/14.
 * 通用加载更多Model
 * 继承该接口, 添加过滤方法
 */

public interface ILoadMoreModel<Bean> extends BaseModel {
    Observable<PageModel<Bean>> loadData(int pageIndex);
}
