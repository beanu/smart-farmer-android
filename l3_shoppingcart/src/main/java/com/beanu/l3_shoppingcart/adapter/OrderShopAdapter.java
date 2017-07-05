package com.beanu.l3_shoppingcart.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.l3_shoppingcart.R;
import com.beanu.l3_shoppingcart.model.bean.CartItem;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 下单页面 商品列表
 * Created by Beanu on 2017/5/15.
 */

public class OrderShopAdapter extends BaseAdapter {

    private Context mContext;
    private List<CartItem> mItemList;

    public OrderShopAdapter(Context context, List<CartItem> mList) {
        mItemList = mList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cart_order_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        CartItem cartItem = mItemList.get(position);
        if (!TextUtils.isEmpty(cartItem.getProductImg())) {
            Glide.with(mContext).load(cartItem.getProductImg()).into(viewHolder.mCartImgOrderItem);
        }

        viewHolder.mCartTxtOrderItemGoodsName.setText(cartItem.getName());
        viewHolder.mCartTxtOrderItemGoodsSku.setText(cartItem.getPress());
        viewHolder.mCartTxtOrderItemGoodsPrice.setText("¥" + cartItem.getPrice() * cartItem.getNum());

        if (cartItem.getNum() > 1) {
            viewHolder.mCartTxtNum.setText("x" + cartItem.getNum());
        }
        return convertView;
    }


    static class ViewHolder {
        private ImageView mCartImgOrderItem;
        private TextView mCartTxtOrderItemGoodsName;
        private TextView mCartTxtOrderItemGoodsSku;
        private TextView mCartTxtOrderItemGoodsPrice;
        private TextView mCartTxtNum;

        ViewHolder(View view) {
            mCartImgOrderItem = (ImageView) view.findViewById(R.id.cart_img_order_item);
            mCartTxtOrderItemGoodsName = (TextView) view.findViewById(R.id.cart_txt_order_item_goods_name);
            mCartTxtOrderItemGoodsSku = (TextView) view.findViewById(R.id.cart_txt_order_item_goods_sku);
            mCartTxtOrderItemGoodsPrice = (TextView) view.findViewById(R.id.cart_txt_order_item_goods_price);
            mCartTxtNum = (TextView) view.findViewById(R.id.cart_txt_order_item_num);
        }
    }
}
