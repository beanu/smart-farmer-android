package com.beanu.sf.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beanu.arad.support.recyclerview.adapter.BaseAdapter;
import com.beanu.sf.R;
import com.beanu.sf.model.bean.LayerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能列表 adapter
 * Created by Beanu on 16/11/4.
 */

public class LayerAdapter extends BaseAdapter<LayerItem, LayerAdapter.LayerHolder> {


    public LayerAdapter(Context context, List<LayerItem> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LayerHolder(inflater.inflate(R.layout.item_layout_layer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LayerHolder) holder).bind(getItem(position));
    }

    static class LayerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_item_layer) TextView mTxtItemLayer;
        Context mContext;

        public LayerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bind(final LayerItem item) {
            mTxtItemLayer.setText(item.getTitle());
            mTxtItemLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Class classname = item.getClsName();

                    if (Activity.class.isAssignableFrom(classname)) {
                        Intent intent = new Intent(mContext, classname);
                        mContext.startActivity(intent);
                    }

                }
            });
        }
    }
}
