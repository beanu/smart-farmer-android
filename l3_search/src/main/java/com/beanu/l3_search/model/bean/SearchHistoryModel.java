package com.beanu.l3_search.model.bean;

import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * 搜索历史
 * Created by Beanu on 2017/6/22.
 */

public class SearchHistoryModel implements Serializable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @NotNull
    @Unique
    private String content;

    private String time;

    public SearchHistoryModel() {
    }

    public SearchHistoryModel(String content) {
        this.content = content;
    }

    public SearchHistoryModel(String time, String content) {
        this.content = content;
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
