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

    //底部点击事件
    public interface OnFooterListener {
        void onRetry();
    }

    private OnFooterListener mOnLoadListener;

    public void setOnLoadListener(OnFooterListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.arad_list_item_stream_status, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull LoadMoreFooter loadMoreFooter) {
        if (loadMoreFooter.listener.hasError()) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.text.setText("加载出错了，请重试");
            holder.text.setEnabled(true);
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnLoadListener != null) {
                        //重新设置状态，并尝试加载
                        holder.text.setText("正在加载更多.....");
                        holder.progress.setVisibility(View.VISIBLE);
                        holder.text.setEnabled(false);
                        mOnLoadListener.onRetry();
                    }
                }
            });
            holder.progress.setVisibility(View.GONE);
        } else if (loadMoreFooter.listener.hasMoreResults()) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.text.setText("正在加载更多.....");
            holder.progress.setVisibility(View.VISIBLE);
            holder.text.setEnabled(false);

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