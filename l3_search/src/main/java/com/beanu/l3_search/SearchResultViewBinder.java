package com.beanu.l3_search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.beanu.l3_search.model.bean.SearchResultModel;
import com.bumptech.glide.Glide;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lizhi
 * @date 2017/11/1.
 */

public class SearchResultViewBinder extends ItemViewBinder<SearchResultModel, SearchResultViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SearchResultModel item) {
        holder.bind(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtContent;
        private ImageView mImg;
        private RelativeLayout mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtContent = itemView.findViewById(R.id.contact_info_name);
            mImg = itemView.findViewById(R.id.contact_info_head);
            mItemView = itemView.findViewById(R.id.rl_item);
        }

        private void bind(final SearchResultModel item) {
            mTxtContent.setText(item.getTitle());

            if (!TextUtils.isEmpty(item.getImgUrl())) {
                Glide.with(itemView.getContext()).load(item.getImgUrl()).into(mImg);
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
                        default:
                            break;
                    }
                }
            });
        }
    }
}
