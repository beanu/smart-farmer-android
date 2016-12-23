package com.beanu.l2_recycleview;

import com.beanu.arad.base.BaseModel;

import java.util.Map;

import rx.Observable;

/**
 * LoadMore M层
 * Created by lizhihua on 2016/12/14.
 */

public interface ILoadMoreModel<B> extends BaseModel {
    Observable<PageModel<B>> loadData(Map<String, Object> params, int pageIndex);
}
