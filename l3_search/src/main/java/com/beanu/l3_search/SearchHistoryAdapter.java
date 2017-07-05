package com.beanu.l3_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beanu.l3_search.model.bean.SearchHistoryModel;

import java.util.ArrayList;

/**
 * 历史记录 adapter
 * Created by Beanu on 2017/6/22.
 */

public class SearchHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SearchHistoryModel> mHistories;

    public SearchHistoryAdapter(Context context, ArrayList<SearchHistoryModel> histories) {
        mContext = context;
        mHistories = histories;
    }

    public void refreshData(ArrayList<SearchHistoryModel> histories) {
        mHistories.clear();
        mHistories = histories;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mHistories.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_listitem_search, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_searchhistory);
            holder.layoutClose = (LinearLayout) convertView.findViewById(R.id.ll_search_close);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.ll_search_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mHistories.get(position).getContent());
        holder.layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchHistoryListener != null) {
                    onSearchHistoryListener.onDelete(mHistories.get(position).getContent());
                }
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchHistoryListener != null) {
                    onSearchHistoryListener.onSelect(mHistories.get(position).getContent());
                }
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private LinearLayout layout;
        public TextView textView;
        public LinearLayout layoutClose;
    }

    public void setOnSearchHistoryListener(OnSearchHistoryListener onSearchHistoryListener) {
        this.onSearchHistoryListener = onSearchHistoryListener;
    }

    private OnSearchHistoryListener onSearchHistoryListener;
}
