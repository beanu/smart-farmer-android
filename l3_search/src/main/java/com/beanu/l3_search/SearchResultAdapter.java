package com.beanu.l3_search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.beanu.arad.support.recyclerview.adapter.BaseAdapter;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 搜索结果Adapter
 * Created by Beanu on 2017/7/1.
 */

public class SearchResultAdapter extends BaseAdapter<SearchResultModel, SearchResultAdapter.ViewHolder> {

    public SearchResultAdapter(Context context, List<SearchResultModel> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtContent;
        private ImageView mImg;
        private RelativeLayout mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtContent = (TextView) itemView.findViewById(R.id.contact_info_name);
            mImg = (ImageView) itemView.findViewById(R.id.contact_info_head);
            mItemView = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }

        private void bind(final SearchResultModel item) {
            mTxtContent.setText(item.getTitle());

            if (!TextUtils.isEmpty(item.getImgUrl())) {
                Glide.with(mContext).load(item.getImgUrl()).into(mImg);
            }

            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (item.getType()) {
                        case 0://图书
                            ARouter.getInstance().build("/app/book/detail").withString("bookId", item.getId()).navigation();
                            break;
                        case 1://资讯
                            ARouter.getInstance().build("/app/news/detail").withString("newsId", item.getId()).navigation();
                            break;
                        case 2://直播课
                            ARouter.getInstance().build("/app/liveLesson/detail").withString("lessonId", item.getId())
                                    .navigation();
                            break;
                        case 3://高清网课
                            ARouter.getInstance().build("/app/onlineLesson/detail").withString("lessonId", item.getId())
                                    .navigation();

                            break;
                    }
                }
            });
        }
    }
}