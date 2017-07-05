package com.beanu.l3_search.mvp.model;

import com.beanu.l3_search.model.bean.SearchHistoryModel;

import java.util.ArrayList;

/**
 * Created by Beanu on 2017/6/22.
 */

public interface OnSearchListener {
    void onSortSuccess(ArrayList<SearchHistoryModel> results);

    void searchSuccess(String value);
}
