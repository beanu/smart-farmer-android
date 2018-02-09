package com.beanu.l3_post.adapter.add_post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.beanu.arad.utils.ViewUtils;
import com.beanu.l3_common.util.ArraysUtil;
import com.beanu.l3_common.widget.MyRadioButton;
import com.beanu.l3_post.R;
import com.beanu.l3_post.model.bean.AddColumnInfoBean;
import com.beanu.l3_post.model.bean.PostTitle;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lizhi
 * @date 2017/11/13.
 */
public class PostTitleViewBinder extends ItemViewBinder<PostTitle, PostTitleViewBinder.ViewHolder> implements CompoundButton.OnCheckedChangeListener {

    private final View.OnFocusChangeListener listener;

    public PostTitleViewBinder(View.OnFocusChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_post_title, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PostTitle postTitle) {
        Context context = holder.itemView.getContext();
        holder.item = postTitle;
        holder.editTitle.setText(postTitle.title);
        if (postTitle.info != null && !ArraysUtil.isEmpty(postTitle.info.getTypes())){
            ViewUtils.setVisibility(View.VISIBLE, holder.scrollCategory, holder.viewSpace);
            Object data = holder.llCategory.getTag(R.id.tag_data);
            if (data != postTitle.info.getTypes()){
                holder.llCategory.setTag(R.id.tag_data, postTitle.info.getTypes());
                holder.llCategory.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(context);
                for (AddColumnInfoBean.TypesBean bean : postTitle.info.getTypes()) {
                    MyRadioButton radio = (MyRadioButton) inflater.inflate(R.layout.item_add_post_type, holder.llCategory, false);
                    radio.setTag(R.id.tag_data, bean);
                    radio.setOnCheckedChangeListener(this);
                    radio.setText(bean.getName());
                    holder.llCategory.addView(radio);
                }
            }
        } else {
            ViewUtils.setVisibility(View.GONE, holder.scrollCategory, holder.viewSpace);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Object data = buttonView.getTag(R.id.tag_data);
        if (data != null && data instanceof AddColumnInfoBean.TypesBean){
            ((AddColumnInfoBean.TypesBean) data).isChecked = isChecked;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llCategory;
        HorizontalScrollView scrollCategory;
        View viewSpace;
        EditText editTitle;

        PostTitle item;

        ViewHolder(View itemView) {
            super(itemView);

            llCategory=itemView.findViewById(R.id.ll_category);
            scrollCategory=itemView.findViewById(R.id.scroll_category);
            viewSpace=itemView.findViewById(R.id.view_space);
            editTitle=itemView.findViewById(R.id.edit_title);


            editTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (item != null && !TextUtils.equals(s, item.title)){
                        item.title = s.toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            editTitle.setOnFocusChangeListener(listener);
        }
    }
}
