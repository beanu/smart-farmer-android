package com.beanu.l2_recyclerview.loadmore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beanu.l2_recycleview.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 加载更多
 * Created by Beanu on 2017/3/7.
 */
public class LoadMoreFooterViewBinder
        extends ItemViewBinder<LoadMoreFooter, LoadMoreFooterViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.arad_list_item_stream_status, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoadMoreFooter loadMoreFooter) {
        if (loadMoreFooter.listener.hasError()) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.text.setText("发生错误");
            holder.progress.setVisibility(View.GONE);
        } else if (loadMoreFooter.listener.hasMoreResults()) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.text.setText("正在加载更多.....");
            holder.progress.setVisibility(View.VISIBLE);

        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progress;
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);

            progress = (ProgressBar) itemView.findViewById(android.R.id.progress);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}