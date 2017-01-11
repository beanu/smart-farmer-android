package com.beanu.l2_recycleview.demo.support.api;

import com.beanu.arad.http.IHttpModel;

/**
 * Created by Beanu on 2017/1/11.
 */

public class HttpModel<T> implements IHttpModel<T> {

    public String error;
    public String msg;
    public T results;

    @Override
    public boolean success() {
        return "false".equals(error);
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
