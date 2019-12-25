package com.beanu.l3_common.model.bean;

import java.io.Serializable;

/**
 * 用户信息
 * Created by Beanu on 16/2/23.
 */
public class User implements Serializable {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
