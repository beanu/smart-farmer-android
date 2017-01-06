package com.beanu.l2_recycleview.demo.support;

/**
 * 顶部图片
 * Created by Beanu on 2017/1/6.
 */

public class IndexImage {

    private String id;     //ID
    private String imgPath;//string图片地址
    private String title; //string 标题
    private String detailUrl;//点击链接

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
