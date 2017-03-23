package com.beanu.l3_shoppingcart;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.l3_shoppingcart.adapter.CartAdapter;
import com.beanu.l3_shoppingcart.mvp.contract.CartContract;
import com.beanu.l3_shoppingcart.mvp.model.CartModelImpl;
import com.beanu.l3_shoppingcart.mvp.presenter.CartPresenterImpl;

import java.text.DecimalFormat;


/**
 * 购物车
 */
public class CartFragment extends ToolBarFragment<CartPresenterImpl, CartModelImpl> implements CartContract.View, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TextView mTxtTotalPrice;
    private TextView mTxtToBuy;
    private CheckBox mAllCheckBox;

    private RelativeLayout mLayoutDeleteMode;
    private TextView mTxtToBuyDelete;
    private CheckBox mAllCheckBoxDelete;


    private CartAdapter mCartAdapter;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartAdapter = new CartAdapter(getActivity(), mPresenter.getProductList(), mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mTxtTotalPrice = (TextView) view.findViewById(R.id.cart_txt_priceTotal);
        mTxtToBuy = (TextView) view.findViewById(R.id.cart_txt_toBuy);
        mAllCheckBox = (CheckBox) view.findViewById(R.id.cart_cb_selectAll);

        //底部删除的时候用
        mLayoutDeleteMode = (RelativeLayout) view.findViewById(R.id.cart_bottom_delete);
        mTxtToBuyDelete = (TextView) view.findViewById(R.id.cart_txt_toBuyDelete);
        mAllCheckBoxDelete = (CheckBox) view.findViewById(R.id.cart_cb_selectAllDelete);

        mAllCheckBoxDelete.setOnClickListener(this);
        mAllCheckBox.setOnClickListener(this);
        mTxtToBuy.setOnClickListener(this);
        mTxtToBuyDelete.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).colorResId(R.color.cart_line).size(1).build());

        mRecyclerView.setAdapter(mCartAdapter);

        if (mPresenter.getProductList().size() == 0) {
            mPresenter.requestCartList();
        }
    }

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void requestCartListSuccess() {
        mCartAdapter.notifyDataSetChanged();
        mPresenter.changed();
    }

    @Override
    public void updateBottomBar(double price, int amount, boolean allChecked) {
        mTxtTotalPrice.setText("¥" + df.format(price));
        mTxtToBuy.setText("结算(" + amount + ")");
        mAllCheckBox.setChecked(allChecked);
    }

    @Override
    public void updateBottomBarDelete(boolean allChecked) {
        mAllCheckBoxDelete.setChecked(allChecked);
    }

    @Override
    public String setupToolBarTitle() {
        return "购物车";
    }

    @Override
    public boolean setupToolBarRightButton(View rightButton) {
        final ImageView btn = (ImageView) rightButton;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.isDeleteMode()) {
                    btn.setImageResource(R.drawable.arad_ic_share_selector);
                    mPresenter.setDeleteMode(false);
                    mPresenter.changed();
                    mLayoutDeleteMode.setVisibility(View.GONE);
                } else {
                    btn.setImageResource(R.drawable.cart_del);
                    mPresenter.setDeleteMode(true);
                    mLayoutDeleteMode.setVisibility(View.VISIBLE);
                }

                mCartAdapter.notifyDataSetChanged();
            }
        });
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cart_cb_selectAll) {
            mPresenter.selectAllGoods(mAllCheckBox.isChecked());
            mCartAdapter.notifyDataSetChanged();
        } else if (id == R.id.cart_cb_selectAllDelete) {
            mPresenter.selectAllGoods(mAllCheckBoxDelete.isChecked());
            mCartAdapter.notifyDataSetChanged();
        } else if (id == R.id.cart_txt_toBuy) {
            //跳转到下单页面
            Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
            startActivity(intent);

        } else if (id == R.id.cart_txt_toBuyDelete) {
            //删除
            mPresenter.removeGoods();
            mCartAdapter.notifyDataSetChanged();
        }
    }
}
