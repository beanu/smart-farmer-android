package com.beanu.l3_search.model.bean;

/**
 * 搜索结果
 * Created by Beanu on 2017/7/1.
 */

public class SearchResultModel {

    private String id;
    private String title;
    private String imgUrl;

    private int type;//0 图书 1资讯 2直播课 3高清网课

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
