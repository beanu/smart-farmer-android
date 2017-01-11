package com.beanu.l2_recycleview.demo.support;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.support.listview.ILoadMoreAdapter;
import com.beanu.arad.support.recyclerview.adapter.BaseHeadLoadMoreAdapter;
import com.beanu.arad.support.viewpager.transforms.HalfTransformer;
import com.beanu.arad.support.viewpager.tricks.ViewPagerAutoScroll;
import com.beanu.arad.support.viewpager.tricks.ViewPagerUtils;
import com.beanu.l2_recycleview.R;
import com.bumptech.glide.Glide;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.List;

/**
 * 列表适配器
 * Created by Beanu on 2016/12/16.
 */

public class DemoHeaderLoadMoreAdapter extends BaseHeadLoadMoreAdapter<News, DemoHeaderLoadMoreAdapter.ViewHolder> {

    private ViewPagerAutoScroll mViewPagerAutoScroll;//viewpager 自动切换
    private List<IndexImage> mIndexImages;


    public DemoHeaderLoadMoreAdapter(Context context, List<News> list, List<IndexImage> imageList, ILoadMoreAdapter listener) {
        super(context, list, listener);
        mIndexImages = imageList;
        mViewPagerAutoScroll = new ViewPagerAutoScroll();

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.demo_header, parent, false), mIndexImages);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HeaderViewHolder) holder).bind();
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public ViewPagerAutoScroll getViewPagerAutoScroll() {
        return mViewPagerAutoScroll;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView mTxtTitle;
        private TextView mTxtDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_item_news);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_item_news_title);
            mTxtDesc = (TextView) itemView.findViewById(R.id.txt_item_news_desc);
        }

        private void bind(News item) {
            Glide.with(mContext).load(item.getImgPath()).into(img);
            mTxtTitle.setText(item.getTitle());
            mTxtDesc.setText(item.getDesc());
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        ViewPager mHeaderViewpager;
        TextView mTvTitle;
        InkPageIndicator mIndicator;

        List<IndexImage> indexImages;
        ViewPagerImageAdapter pagerAdapter;

        HeaderViewHolder(View itemView, List<IndexImage> list) {
            super(itemView);

            mHeaderViewpager = (ViewPager) itemView.findViewById(R.id.header_viewpager);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mIndicator = (InkPageIndicator) itemView.findViewById(R.id.ink_indicator);

            indexImages = list;
            pagerAdapter = new ViewPagerImageAdapter(mContext, indexImages);

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

        void bind() {
            pagerAdapter.notifyDataSetChanged();
        }

    }
}