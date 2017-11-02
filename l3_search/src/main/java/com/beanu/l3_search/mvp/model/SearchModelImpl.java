package com.beanu.l3_search.mvp.model;

import com.beanu.arad.Arad;
import com.beanu.arad.http.RxHelper;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_search.model.APISearchService;
import com.beanu.l3_search.model.bean.SearchHistoryModel;
import com.beanu.l3_search.model.bean.SearchResult;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.beanu.l3_search.mvp.contract.SearchContract;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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

        return Observable.create(new ObservableOnSubscribe<SearchResult>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResult> e) throws Exception {
                Thread.sleep(2000);
                SearchResult result = new SearchResult();
                result.setBookList(mockSearchResultModels(0));
                result.setDirectList(mockSearchResultModels(2));
                result.setInforList(mockSearchResultModels(1));
                result.setRecordingList(mockSearchResultModels(2));
                e.onNext(result);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


//        return API.getInstance(APISearchService.class).searchResult("", value)
//                .compose(RxHelper.<SearchResult>handleResult());
    }

    private List<SearchResultModel> mockSearchResultModels(int type){
        List<SearchResultModel> list = new ArrayList<>();
        for (int i=0;i<5;++i){
            SearchResultModel model = new SearchResultModel();
            model.setId("id"+i);
            model.setImgUrl("http://img5.imgtn.bdimg.com/it/u=1187077172,2563639533&fm=200&gp=0.jpg");
            model.setTitle("title"+i);
            model.setType(type);
            list.add(model);
        }
        return list;
    }
}