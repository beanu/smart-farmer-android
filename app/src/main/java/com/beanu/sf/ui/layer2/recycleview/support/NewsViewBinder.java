package com.beanu.sf.ui.layer2.recycleview.support;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.sf.R;
import com.bumptech.glide.Glide;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 新闻item binder
 * Created by Beanu on 2017/7/13.
 */
public class NewsViewBinder extends ItemViewBinder<News, NewsViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull News news) {

        Context context = holder.itemView.getContext();

        Glide.with(context).load(news.getImgPath()).into(holder.img);
        holder.mTxtTitle.setText(news.getTitle());
        holder.mTxtDesc.setText(news.getDesc());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView mTxtTitle;
        private TextView mTxtDesc;

        ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_item_news);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txt_item_news_title);
            mTxtDesc = (TextView) itemView.findViewById(R.id.txt_item_news_desc);
        }
    }
}