package com.beanu.l3_shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.support.recyclerview.adapter.BaseAdapter;
import com.beanu.l3_shoppingcart.R;
import com.beanu.l3_shoppingcart.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.presenter.CartPresenterImpl;
import com.beanu.l3_shoppingcart.widget.InDeNumber;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 购物车adapter
 * Created by Beanu on 2017/3/10.
 */

public class CartAdapter extends BaseAdapter<CartItem, CartAdapter.ItemViewHolder> {

    private OnShoppingCartListener mCartListener;
    private CartPresenterImpl mCartPresenter;

    public interface OnShoppingCartListener {
        public void changed();
    }


    public CartAdapter(Context context, List<CartItem> list, OnShoppingCartListener listener) {
        super(context, list);
        mCartListener = listener;
        if (listener instanceof CartPresenterImpl) {
            mCartPresenter = (CartPresenterImpl) listener;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.cart_shop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(getItem(position));
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        CheckBox mCheckBox;
        ImageView mImgFace;
        TextView mTxtTitle;
        TextView mTxtSKU;
        TextView mTxtPrice;
        InDeNumber mInDeNumber;

        DecimalFormat df = new DecimalFormat("0.00");

        ItemViewHolder(View view) {
            super(view);
            mCheckBox = (CheckBox) view.findViewById(R.id.cart_shop_cb);
            mImgFace = (ImageView) view.findViewById(R.id.cart_shop_img);
            mTxtTitle = (TextView) view.findViewById(R.id.cart_shop_name);
            mTxtSKU = (TextView) view.findViewById(R.id.cart_shop_sku);
            mTxtPrice = (TextView) view.findViewById(R.id.cart_shop_price);
            mInDeNumber = (InDeNumber) view.findViewById(R.id.cart_shop_indeNumber);
        }

        private void bind(final CartItem item) {

            mCheckBox.setChecked(mCartPresenter.isDeleteMode() ? item.isDelete_checked() : item.isCart_checked());
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCartPresenter.isDeleteMode()) {
                        item.setDelete_checked(mCheckBox.isChecked());
                        mCartListener.changed();
                    } else {
                        item.setCart_checked(mCheckBox.isChecked());
                        mCartListener.changed();
                    }
                }
            });

            if (!TextUtils.isEmpty(item.getFaceImgPath())) {
                Glide.with(mContext).load(item.getFaceImgPath()).into(mImgFace);
            }
            mTxtTitle.setText(item.getName());
            mTxtSKU.setText(item.getSku());
            mTxtPrice.setText(df.format(item.getPrice()) + "元");
            mInDeNumber.setNum(item.getCart_amount());
            mInDeNumber.setListener(new InDeNumber.OnChangeListener() {
                @Override
                public void notifyDataChanged(int num) {
                    item.setCart_amount(num);
                    mCartListener.changed();
                }
            });

        }
    }
}
