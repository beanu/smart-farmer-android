package com.beanu.sf.ui.layer2.recycleview.support;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.beanu.arad.support.viewpager.transforms.HalfTransformer;
import com.beanu.arad.support.viewpager.tricks.ViewPagerAutoScroll;
import com.beanu.arad.support.viewpager.tricks.ViewPagerUtils;
import com.beanu.sf.R;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lizhi
 * @date 2017/11/1.
 */

public class HeaderViewBinder extends ItemViewBinder<DemoHeader, HeaderViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.demo_header, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DemoHeader item) {
        holder.bind(item);
    }

    @Override
    protected void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.mViewPagerAutoScroll.start();
    }

    @Override
    protected void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mViewPagerAutoScroll.stop();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ViewPager mHeaderViewpager;
        TextView mTvTitle;
        InkPageIndicator mIndicator;

        final List<IndexImage> indexImages = new ArrayList<>();
        ViewPagerImageAdapter pagerAdapter;
        ViewPagerAutoScroll mViewPagerAutoScroll;//viewpager 自动切换

        ViewHolder(View itemView) {
            super(itemView);
            mViewPagerAutoScroll = new ViewPagerAutoScroll();


            mHeaderViewpager = itemView.findViewById(R.id.header_viewpager);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mIndicator = itemView.findViewById(R.id.ink_indicator);

            pagerAdapter = new ViewPagerImageAdapter(itemView.getContext(), indexImages);

            mHeaderViewpager.setAdapter(pagerAdapter);
            mHeaderViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    setTitle(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            mHeaderViewpager.setPageTransformer(true, new HalfTransformer());
            mHeaderViewpager.setOffscreenPageLimit(3);
            ViewPagerUtils.setSliderTransformDuration(mHeaderViewpager, 1000, new DecelerateInterpolator());

            mIndicator.setViewPager(mHeaderViewpager);
            mViewPagerAutoScroll.setmViewPager(mHeaderViewpager);
            setTitle(0);
        }

        private void setTitle(int position) {
            if (null != indexImages && indexImages.size() > 0) {
                mTvTitle.setText(indexImages.get(position).getTitle());//设置轮播图的文本
            }
        }

        void bind(DemoHeader item) {
            indexImages.clear();
            indexImages.addAll(item.indexImages);

            pagerAdapter.notifyDataSetChanged();
        }
    }
}
