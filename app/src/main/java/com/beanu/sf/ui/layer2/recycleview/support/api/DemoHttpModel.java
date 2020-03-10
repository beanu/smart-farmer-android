package com.beanu.sf.ui.layer2.recycleview.support.api;

import com.beanu.arad.http.IHttpModel;

/**
 * Created by Beanu on 2017/1/11.
 */

public class DemoHttpModel<T> implements IHttpModel<T> {

    public int error;
    public String msg;
    public T results;

    @Override
    public boolean success() {
        return error == 0;
    }

    @Override
    public int getErrorCode() {
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
