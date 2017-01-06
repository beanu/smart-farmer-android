package com.beanu.l2_recycleview.demo.support;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 顶部广告位
 * Created by Beanu on 2016/12/20.
 */

public class ViewPagerImageAdapter extends PagerAdapter {
    private List<IndexImage> indexImages;
    private Context mContext;

    public ViewPagerImageAdapter(Context context, List<IndexImage> indexImages) {
        this.indexImages = indexImages;
        mContext = context;
    }

    @Override
    public int getCount() {
        return indexImages == null ? 0 : indexImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ImageView iv = new ImageView(mContext);

        if (!TextUtils.isEmpty(indexImages.get(position).getImgPath())) {
            Glide.with(mContext).load(indexImages.get(position).getImgPath()).into(iv);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, indexImages.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        linearLayout.addView(iv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        container.addView(linearLayout);
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
