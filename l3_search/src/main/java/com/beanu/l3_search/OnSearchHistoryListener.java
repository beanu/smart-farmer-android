package com.beanu.l3_search;

/**
 * 搜索监听
 * Created by Beanu on 2017/6/22.
 */

public interface OnSearchHistoryListener {
    void onDelete(String key);

    void onSelect(String content);
}
