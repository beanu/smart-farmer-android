package com.beanu.l3_shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;

/**
 * 选择收货地址 / 我的地址
 */
public class AddressChooseActivity extends ToolBarActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TextView mTxtAddressAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_address_choose);

        mRecyclerView = (RecyclerView) findViewById(R.id.cart_address_recycleview);
        mTxtAddressAdd = (TextView) findViewById(R.id.cart_txt_address_add);
        mTxtAddressAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cart_txt_address_add) {
            Intent intent = new Intent(this, AddressAddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public String setupToolBarTitle() {
        return "选择地址";
    }
}
