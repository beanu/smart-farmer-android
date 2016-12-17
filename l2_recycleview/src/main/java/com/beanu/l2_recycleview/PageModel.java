package com.beanu.l2_recycleview;

import java.util.List;

/**
 * 翻页数据
 * Created by Beanu on 16/9/26.
 */

public class PageModel<T> {

    public int totalPage;
    public List<T> dataList;
    public int currentPage;
}
