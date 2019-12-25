package com.beanu.l3_common.model;

import com.beanu.arad.http.IHttpModel;

import java.io.Serializable;

/**
 * 基础实体类
 */
public class HttpModel<T> implements IHttpModel<T>, Serializable {
    private int succeed;
    private String sucInfo;
    private T dataInfo;

    @Override
    public boolean success() {
        return succeed == 400;
    }

    @Override
    public int getErrorCode() {
        return succeed;
    }

    @Override
    public String getMsg() {
        return sucInfo;
    }

    @Override
    public T getResults() {
        return dataInfo;
    }
}
