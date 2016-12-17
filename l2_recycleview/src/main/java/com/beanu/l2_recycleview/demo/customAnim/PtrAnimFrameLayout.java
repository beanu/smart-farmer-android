package com.beanu.l2_recycleview.demo.customAnim;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 自定义 下拉刷新动画
 * Created by Beanu on 2016/12/17.
 */

public class PtrAnimFrameLayout extends PtrFrameLayout {

    PtrAnimHeader mHeaderView;

    public PtrAnimFrameLayout(Context context) {
        super(context);
        initView();
    }

    public PtrAnimFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PtrAnimFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mHeaderView = new PtrAnimHeader(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }

    @Override
    public PtrAnimHeader getHeaderView() {
        return mHeaderView;
    }
}
