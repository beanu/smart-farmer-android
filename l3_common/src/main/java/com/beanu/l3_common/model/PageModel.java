package com.beanu.l3_common.model;

import com.beanu.arad.http.IPageModel;

import java.util.List;

/**
 * 翻页数据
 * Created by Beanu on 16/9/26.
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
