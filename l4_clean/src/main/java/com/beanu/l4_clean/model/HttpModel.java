package com.beanu.l4_clean.model;

import com.beanu.arad.http.IHttpModel;

import java.io.Serializable;

/**
 * 基础实体类
 */
public class HttpModel<T> implements IHttpModel<T>, Serializable {

    public String error;
    public String msg;
    public T results;

    @Override
    public boolean success() {
        return error.equals("false");
    }

    @Override
    public String getErrorCode() {
        return error;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getResults() {
        return results;
    }
}
