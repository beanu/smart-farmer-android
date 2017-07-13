package com.beanu.l3_search.mvp.presenter;

import com.beanu.l3_search.model.bean.SearchHistoryModel;
import com.beanu.l3_search.model.bean.SearchResult;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.beanu.l3_search.mvp.contract.SearchContract;
import com.beanu.l3_search.mvp.model.OnSearchListener;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Beanu on 2017/06/22
 */

public class SearchPresenterImpl extends SearchContract.Presenter implements OnSearchListener {

    private static final int historyMax = 5;

    @Override
    public void remove(String key) {
        mModel.remove(key);
        mModel.sortHistory(this);
    }

    @Override
    public void clear() {
        mModel.clear();
        mModel.sortHistory(this);
    }

    @Override
    public void sortHistory() {
        mModel.sortHistory(this);
    }

    @Override
    public void search(String value) {
        mModel.save(value);
        mModel.search(value, this);
    }

    @Override
    public void searchResult(String value) {
        mModel.searchResult(value).subscribe(new Observer<SearchResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(@NonNull SearchResult searchResult) {
                ArrayList<SearchResultModel> arrayList = new ArrayList<>();

                if (searchResult.getBookList() != null) {
                    for (SearchResultModel book : searchResult.getBookList()) {
                        book.setType(0);
                        arrayList.add(book);
                    }
                }

                if (searchResult.getInforList() != null) {
                    for (SearchResultModel info : searchResult.getInforList()) {
                        info.setType(1);
                        arrayList.add(info);
                    }
                }

                if (searchResult.getDirectList() != null) {
                    for (SearchResultModel live : searchResult.getDirectList()) {
                        live.setType(2);
                        arrayList.add(live);
                    }
                }
                if (searchResult.getRecordingList() != null) {
                    for (SearchResultModel record : searchResult.getRecordingList()) {
                        record.setType(3);
                        arrayList.add(record);
                    }
                }

                mView.searchResult(arrayList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onSortSuccess(ArrayList<SearchHistoryModel> results) {
        mView.showHistories(results);
    }

    @Override
    public void searchSuccess(String value) {
        mView.searchSuccess(value);
    }
}