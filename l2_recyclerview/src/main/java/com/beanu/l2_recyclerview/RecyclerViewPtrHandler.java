package com.beanu.l2_recyclerview;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by lizhihua on 2017/2/27.
 */

public abstract class RecyclerViewPtrHandler extends PtrDefaultHandler {
    private RecyclerView recyclerView;
    private boolean isAppBarLayoutExpanded = true;

    public RecyclerViewPtrHandler(RecyclerView recyclerView) {
        this(null, recyclerView);
    }

    public RecyclerViewPtrHandler(AppBarLayout appBarLayout, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    isAppBarLayoutExpanded = verticalOffset == 0;
                }
            });
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return (recyclerView == null
                || !recyclerView.isEnabled()
                || recyclerView.getVisibility() != View.VISIBLE
                || recyclerView.getChildCount() == 0
                || !recyclerView.canScrollVertically(-1))
                && isAppBarLayoutExpanded
                && super.checkCanDoRefresh(frame, content, header);
    }
}
