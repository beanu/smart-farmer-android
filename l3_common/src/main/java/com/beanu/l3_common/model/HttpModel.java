package com.beanu.l3_common.model;

import com.beanu.arad.http.IHttpModel;

import java.io.Serializable;

/**
 * 基础实体类
 */
public class HttpModel<T> implements IHttpModel<T>, Serializable {
    public String succeed;
    public String sucInfo;
    public T dataInfo;

    @Override
    public boolean success() {
        return succeed.equals("000");
    }

    @Override
    public String getErrorCode() {
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
