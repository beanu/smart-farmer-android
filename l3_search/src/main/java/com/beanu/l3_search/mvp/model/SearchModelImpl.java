package com.beanu.l3_search.mvp.model;

import com.beanu.arad.Arad;
import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_search.model.APISearchService;
import com.beanu.l3_search.model.bean.SearchHistoryModel;
import com.beanu.l3_search.model.bean.SearchResult;
import com.beanu.l3_search.mvp.contract.SearchContract;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Beanu on 2017/06/22
 */

public class SearchModelImpl implements SearchContract.Model {

    @Override
    public void save(String value) {
        SearchHistoryModel model = new SearchHistoryModel(value);
        Arad.db.save(model);
    }

    @Override
    public void search(String value, OnSearchListener onSearchListener) {
        onSearchListener.searchSuccess(value);
    }

    @Override
    public void remove(String key) {

        Arad.db.getLiteOrm().delete(new WhereBuilder(SearchHistoryModel.class, "content=?", new String[]{key}));
    }

    @Override
    public void clear() {
        Arad.db.getLiteOrm().deleteAll(SearchHistoryModel.class);
    }

    @Override
    public void sortHistory(OnSearchListener onSearchListener) {

        ArrayList<SearchHistoryModel> list = Arad.db.findAll(SearchHistoryModel.class, "id");
        if (list == null) {
            list = new ArrayList<>();
        }
        onSearchListener.onSortSuccess(list);
    }

    @Override
    public Observable<SearchResult> searchResult(String value) {

//        String subjectId = null;
//        if (AppHolder.getInstance().user != null) {
//            subjectId = AppHolder.getInstance().user.getSubjectId();
//        }

        return API.getInstance(APISearchService.class).searchResult("", value)
                .compose(RxHelper.<SearchResult>handleResult());
    }
}