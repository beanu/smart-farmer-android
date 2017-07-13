package com.beanu.sf.ui.layer3.shoppingcart;

import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_shoppingcart.CartFragment;
import com.beanu.sf.R;


public class CartDemoActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_demo);


        CartFragment cartFragment = CartFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, cartFragment, "fragment")
                .commit();

    }
}
