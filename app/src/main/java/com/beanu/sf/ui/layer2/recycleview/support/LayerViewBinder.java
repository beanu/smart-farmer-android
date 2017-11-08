package com.beanu.sf.ui.layer2.recycleview.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beanu.sf.R;
import com.beanu.sf.model.bean.LayerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lizhi
 * @date 2017/11/1.
 */

public class LayerViewBinder extends ItemViewBinder<LayerItem, LayerViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_layout_layer, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final LayerItem item) {

        holder.mTxtItemLayer.setText(item.getTitle());
        holder.mTxtItemLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Class classname = item.getClsName();

                if (Activity.class.isAssignableFrom(classname)) {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, classname);
                    context.startActivity(intent);
                }

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_item_layer)
        TextView mTxtItemLayer;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
