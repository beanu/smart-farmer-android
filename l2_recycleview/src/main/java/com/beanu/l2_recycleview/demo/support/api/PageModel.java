package com.beanu.l2_recycleview.demo.support.api;

import com.beanu.arad.http.IPageModel;

import java.util.List;

/**
 * Created by Beanu on 2017/1/11.
 */

public class PageModel<T> implements IPageModel<T> {

    public int totalPage;
    public List<T> dataList;
    public int currentPage;

    @Override
    public int getTotalPage() {
        return totalPage;
    }

    @Override
    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }
}
