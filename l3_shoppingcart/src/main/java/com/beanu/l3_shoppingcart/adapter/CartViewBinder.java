package com.beanu.l3_shoppingcart.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.l3_shoppingcart.R;
import com.beanu.l3_shoppingcart.model.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.presenter.CartPresenterImpl;
import com.beanu.l3_shoppingcart.widget.InDeNumber;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

import java.text.DecimalFormat;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lizhi
 * @date 2017/11/1.
 */

public class CartViewBinder extends ItemViewBinder<CartItem, CartViewBinder.ItemViewHolder> {

    private OnShoppingCartListener mCartListener;
    private CartPresenterImpl mCartPresenter;

    public CartViewBinder(OnShoppingCartListener cartListener, CartPresenterImpl cartPresenter) {
        this.mCartListener = cartListener;
        this.mCartPresenter = cartPresenter;
    }

    public interface OnShoppingCartListener {
        void changed();
    }

    @NonNull
    @Override
    protected ItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.cart_shop_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull CartItem item) {
        holder.bind(item);
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

        private void bind(final com.beanu.l3_shoppingcart.model.bean.CartItem item) {

            mCheckBox.setChecked(mCartPresenter.isDeleteMode() ? item.isDelete_checked() : (item.isSelect() == 1));
            mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCartPresenter.isDeleteMode()) {
                        item.setDelete_checked(mCheckBox.isChecked());
                        mCartListener.changed();
                    } else {
                        item.setSelect(mCheckBox.isChecked() ? 1 : 0);
                        mCartListener.changed();
                        mCartPresenter.updateCartShop(item);
                    }
                }
            });

            if (!TextUtils.isEmpty(item.getProductImg())) {
                Glide.with(itemView.getContext()).load(item.getProductImg()).into(mImgFace);
            }
            mTxtTitle.setText(item.getName());
            mTxtSKU.setText(item.getPress());
            mTxtPrice.setText(df.format(item.getPrice()) + "元");
            mInDeNumber.setNum(item.getNum());
            mInDeNumber.setListener(new InDeNumber.OnChangeListener() {
                @Override
                public void notifyDataChanged(int num) {
                    item.setNum(num);
                    mCartListener.changed();
                    mCartPresenter.updateCartShop(item);
                }
            });

        }
    }
}
