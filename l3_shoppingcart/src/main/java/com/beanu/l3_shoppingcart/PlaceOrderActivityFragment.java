package com.beanu.l3_shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.widget.LinearLayoutForListView;
import com.beanu.l3_common.model.bean.EventModel;
import com.beanu.l3_shoppingcart.adapter.OrderShopAdapter;
import com.beanu.l3_shoppingcart.model.bean.AddressItem;
import com.beanu.l3_shoppingcart.model.bean.CartItem;
import com.beanu.l3_shoppingcart.mvp.contract.PlaceOrderContract;
import com.beanu.l3_shoppingcart.mvp.model.PlaceOrderModelImpl;
import com.beanu.l3_shoppingcart.mvp.presenter.PlaceOrderPresenterImpl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 下订单
 */
public class PlaceOrderActivityFragment extends ToolBarFragment<PlaceOrderPresenterImpl, PlaceOrderModelImpl> implements View.OnClickListener, PlaceOrderContract.View {

    private static final int REQUEST_CHOOSE = 0;
    private ImageView mImgAddressTag;
    private TextView mTxtAddressUserName;
    private TextView mTxtAddressUserPhone;
    private TextView mTxtAddress;
    private RelativeLayout mRlAddress;
    private LinearLayoutForListView mGoodsListview;
    private RelativeLayout mRlPlaceOrderExpressFee;
    private TextView mCartTxtPriceTotal;
    private TextView mTxtCartInfo;
    private TextView mTxtToPay;

    ArrayList<CartItem> shopList;
    double priceSum;
    int goodsCount;

    OrderShopAdapter mOrderShopAdapter;
    DecimalFormat df = new DecimalFormat("0.00");

    public PlaceOrderActivityFragment() {
    }

    public static PlaceOrderActivityFragment newInstance(Bundle bundle) {

        PlaceOrderActivityFragment fragment = new PlaceOrderActivityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            shopList = bundle.getParcelableArrayList("shopList");
            priceSum = bundle.getDouble("priceSum", 0);
            goodsCount = bundle.getInt("goodsCount");
        } else {
            shopList = new ArrayList<>();
        }

        //删除其中没有选中的数据
        Iterator<CartItem> iterator = shopList.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.isSelect() == 0) {
                iterator.remove();
            }
        }

        mOrderShopAdapter = new OrderShopAdapter(getActivity(), shopList);

        Arad.bus.register(this);
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

        mTxtCartInfo.setText(String.format("共%s件商品  小计:¥%s", goodsCount, df.format(priceSum)));
        mCartTxtPriceTotal.setText("¥" + df.format(priceSum));

        mGoodsListview.setAdapter(mOrderShopAdapter);

        //获取我的默认地址
        mPresenter.requestMyAddressDefault();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHOOSE) {
            AddressItem addressItem = data.getParcelableExtra("address");
            refreshAddress(addressItem);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Arad.bus.unregister(this);
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
        mCartTxtPriceTotal = (TextView) view.findViewById(R.id.cart_txt_priceTotal);
        mTxtCartInfo = (TextView) view.findViewById(R.id.txt_cart_info);
        mTxtToPay = (TextView) view.findViewById(R.id.cart_txt_toPay);

        mRlAddress.setOnClickListener(this);
        mTxtToPay.setOnClickListener(this);
    }

    //更新地址视图
    private void refreshAddress(AddressItem address) {
        mTxtAddressUserName.setText(address.getLink_name());
        mTxtAddressUserPhone.setText(address.getLink_phone());
        mTxtAddress.setText(address.getProvince_cn() + address.getCity_cn() + address.getCounty_cn() + address.getLink_address());
        mTxtAddress.setTag(address.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel.CartBuySuccess item) {
        getActivity().finish();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_address) {
            Intent intent = new Intent(getActivity(), AddressChooseActivity.class);
            startActivityForResult(intent, REQUEST_CHOOSE);
        } else if (id == R.id.cart_txt_toPay) {

            String addressId = (String) mTxtAddress.getTag();
            String cartIds = "";
            for (CartItem cartItem : shopList) {
                cartIds += cartItem.getId() + ",";
            }

            mPresenter.createBookOrder(addressId, cartIds);
        }
    }

    @Override
    public void refreshDefaultAddress(AddressItem addressItem) {
        refreshAddress(addressItem);

    }

    @Override
    public void createBookOrderSuccess(String orderId) {
        Intent intent = new Intent(getActivity(), CartPayActivity.class);
        intent.putExtra("priceSum", Double.parseDouble(df.format(priceSum)));
        intent.putExtra("orderId", orderId);
        startActivity(intent);
    }
}
