package com.beanu.l3_common.support;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 *
 * @author lizhihua
 * @date 2016/12/12
 */

public class NestedScrollHelper implements View.OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //当前 View 可以滚动则将事件交给 View 处理；否则将事件交由其父类处理
        if (canVerticalScroll(view) && motionEvent.getAction() != MotionEvent.ACTION_UP) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }

    /**
     * View 竖直方向是否可以滚动
     *
     * @param view 需要判断的 View
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(View view) {
        if (view instanceof EditText) {
            return canVerticalScroll((EditText) view);
        }
        //滚动的距离
        int scrollY = view.getScrollY();
        //控件内容的总高度
        int scrollRange = view.getLayoutParams().height;
        //控件实际显示的高度
        int scrollExtent = view.getLayoutParams().height - view.getPaddingTop() - view.getPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference);
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {

        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
}
