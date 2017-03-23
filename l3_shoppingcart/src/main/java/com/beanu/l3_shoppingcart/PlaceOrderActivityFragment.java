package com.beanu.l3_shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.widget.LinearLayoutForListView;

/**
 * 下订单
 */
public class PlaceOrderActivityFragment extends ToolBarFragment implements View.OnClickListener {

    private ImageView mImgAddressTag;
    private TextView mTxtAddressUserName;
    private TextView mTxtAddressUserPhone;
    private TextView mTxtAddress;
    private RelativeLayout mRlAddress;
    private LinearLayoutForListView mGoodsListview;
    private RelativeLayout mRlPlaceOrderExpressFee;
    private TextView mCartTxtPriceTotalTag;
    private TextView mCartTxtPriceTotal;
    private TextView mTxtToPay;

    public PlaceOrderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment_place_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);


    }

    //初始化视图
    private void initView(View view) {
        mImgAddressTag = (ImageView) view.findViewById(R.id.cart_img_address_tag);
        mTxtAddressUserName = (TextView) view.findViewById(R.id.cart_txt_address_userName);
        mTxtAddressUserPhone = (TextView) view.findViewById(R.id.cart_txt_address_userPhone);
        mTxtAddress = (TextView) view.findViewById(R.id.cart_txt_address);
        mRlAddress = (RelativeLayout) view.findViewById(R.id.rl_address);
        mGoodsListview = (LinearLayoutForListView) view.findViewById(R.id.cart_goods_listview);
        mRlPlaceOrderExpressFee = (RelativeLayout) view.findViewById(R.id.rl_place_order_express_fee);
        mCartTxtPriceTotalTag = (TextView) view.findViewById(R.id.cart_txt_priceTotal_tag);
        mCartTxtPriceTotal = (TextView) view.findViewById(R.id.cart_txt_priceTotal);
        mTxtToPay = (TextView) view.findViewById(R.id.cart_txt_toPay);

        mRlAddress.setOnClickListener(this);
        mTxtToPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_address) {
            Intent intent = new Intent(getActivity(), AddressChooseActivity.class);
            startActivity(intent);
        } else if (id == R.id.cart_txt_toPay) {
            Intent intent = new Intent(getActivity(), CartPayActivity.class);
            startActivity(intent);
        }
    }
}
