package com.beanu.l3_shoppingcart;

import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;

/**
 * 添加收货地址
 */
public class AddressAddActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_address_add);
    }


    @Override
    public String setupToolBarTitle() {
        return "添加地址";
    }
}
