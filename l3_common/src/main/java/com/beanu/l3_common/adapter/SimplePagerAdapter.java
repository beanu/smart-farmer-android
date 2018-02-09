package com.beanu.l3_common.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * @author lizhihua
 * @date 2017/2/28
 */

public abstract class SimplePagerAdapter<T> extends PagerAdapter{
    protected SparseArray<View> cachedView = new SparseArray<>();
    protected List<T> list;
    private int maxCount = 20;
    private int trimCount = 5;

    public SimplePagerAdapter(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getTrimCount() {
        return trimCount;
    }

    public void setTrimCount(int trimCount) {
        this.trimCount = trimCount;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position);
        if (view == null){
            view = createView(container, list.get(position), position);
            cachedView.put(position, view);
            if (cachedView.size() > maxCount){
                trim(position);
            }
        }
        container.addView(view);
        return view;
    }

    protected void trim(int currentPosition){
        //TODO it's bad

    }

    private View getView(int position){
        return cachedView.get(position);
    }

    protected abstract View createView(ViewGroup container, T data, int position);
}
