package com.beanu.l3_shoppingcart;

import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

/**
 * 下订单页面
 */
public class PlaceOrderActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_place_order);

        PlaceOrderActivityFragment fragment = PlaceOrderActivityFragment.newInstance(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment, "placeOrder").commit();
    }

    @Override
    public void initTopBar(QMUITopBarLayout topBarLayout) {
        topBarLayout.setTitle("下单");
        topBarLayout.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }
}
