package com.beanu.l3_guide.video;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beanu.l3_guide.R;

/**
 * 向导
 * Created by Beanu on 16/9/1.
 */
public class GuideVideoAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    TextView mTextView;


    public GuideVideoAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        View view = layoutInflater.inflate(R.layout.item_guide, collection, false);
        mTextView = (TextView) view.findViewById(R.id.guide_text);

        mTextView.setText("制作美食,从此是个享受" + position);

        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
