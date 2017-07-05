package com.beanu.l3_search.model.bean;

import java.util.List;

/**
 * Created by Beanu on 2017/7/1.
 */

public class SearchResult {

    private List<SearchResultModel> bookList;
    private List<SearchResultModel> inforList;
    private List<SearchResultModel> directList;
    private List<SearchResultModel> recordingList;

    public List<SearchResultModel> getBookList() {
        return bookList;
    }

    public void setBookList(List<SearchResultModel> bookList) {
        this.bookList = bookList;
    }

    public List<SearchResultModel> getInforList() {
        return inforList;
    }

    public void setInforList(List<SearchResultModel> inforList) {
        this.inforList = inforList;
    }

    public List<SearchResultModel> getDirectList() {
        return directList;
    }

    public void setDirectList(List<SearchResultModel> directList) {
        this.directList = directList;
    }

    public List<SearchResultModel> getRecordingList() {
        return recordingList;
    }

    public void setRecordingList(List<SearchResultModel> recordingList) {
        this.recordingList = recordingList;
    }
}
