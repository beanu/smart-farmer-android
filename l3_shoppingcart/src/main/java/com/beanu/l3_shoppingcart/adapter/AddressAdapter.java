package com.beanu.l3_shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.beanu.arad.support.recyclerview.adapter.BaseAdapter;
import com.beanu.l3_shoppingcart.R;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;

import java.util.List;

/**
 * 维护地址列表 adapter
 * Created by Beanu on 2017/3/10.
 */

public class AddressAdapter extends BaseAdapter<AddressItem, AddressAdapter.ItemViewHolder> {

    private OnAddressListener mAddressListener;

    public interface OnAddressListener {
        public void deleteAddress(int position);

        public void editAddress(int position);

        public void chooseAddress(int position);

        public void defaultAddress(int position);
    }


    public AddressAdapter(Context context, List<AddressItem> list, OnAddressListener listener) {
        super(context, list);
        mAddressListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.cart_address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ItemViewHolder) holder).bind(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressListener.chooseAddress(position);
            }
        });

        ((ItemViewHolder) holder).llDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressListener.deleteAddress(position);
            }
        });

        ((ItemViewHolder) holder).llEditorAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressListener.editAddress(position);
            }
        });

        ((ItemViewHolder) holder).cbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressListener.defaultAddress(position);
            }
        });
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        RadioButton cbDefault;
        TextView tvName;
        TextView tvPhoneNumber;
        TextView tvAddress;

        TextView llEditorAddress;
        TextView llDeleteAddress;

        ItemViewHolder(View view) {
            super(view);
            cbDefault = (RadioButton) view.findViewById(R.id.cbDefault);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            llEditorAddress = (TextView) view.findViewById(R.id.llEditorAddress);
            llDeleteAddress = (TextView) view.findViewById(R.id.llDeleteAddress);
        }

        private void bind(AddressItem item) {

            tvName.setText(item.getLink_name());
            tvPhoneNumber.setText(item.getLink_phone());
            tvAddress.setText(item.getProvince_cn() + item.getCity_cn() + item.getCounty_cn() + item.getLink_address());

            cbDefault.setChecked(item.getIs_default() == 1);
        }
    }
}
