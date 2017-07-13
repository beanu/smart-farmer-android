package com.beanu.l3_search.mvp.contract;

import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.l3_search.model.bean.SearchHistoryModel;
import com.beanu.l3_search.model.bean.SearchResult;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.beanu.l3_search.mvp.model.OnSearchListener;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * 搜索 契约类
 * Created by Beanu on 2017/6/22.
 */

public interface SearchContract {

    public interface View extends BaseView {
        void showHistories(ArrayList<SearchHistoryModel> results);

        void searchSuccess(String value);

        void searchResult(ArrayList<SearchResultModel> resultModels);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void remove(String key);

        public abstract void clear();

        public abstract void sortHistory();

        public abstract void search(String value);

        public abstract void searchResult(String value);
    }

    public interface Model extends BaseModel {
        void save(String value);

        void search(String value, OnSearchListener onSearchListener);

        void remove(String key);

        void clear();

        void sortHistory(OnSearchListener onSearchListener);

        Observable<SearchResult> searchResult(String value);
    }

}