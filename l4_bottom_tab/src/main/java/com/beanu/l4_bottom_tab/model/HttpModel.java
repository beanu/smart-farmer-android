package com.beanu.l4_bottom_tab.model;

import java.io.Serializable;

/**
 * 基础实体类
 */
public class HttpModel<T> implements Serializable {
    public String error;
    public String msg;
    public T results;


    public boolean success() {
        return error.equals("false");
    }
}
