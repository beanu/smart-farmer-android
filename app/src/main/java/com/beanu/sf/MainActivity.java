package com.beanu.sf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.sf.ui.layer1.OneActivity;
import com.beanu.sf.ui.layer2.imageselector.ImageSelectorMainActivity;
import com.beanu.sf.ui.layer3.guide.GuideDemoActivity;
import com.beanu.sf.ui.layer3.shoppingcart.CartDemoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends ToolBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_one_arad, R.id.btn_two_function, R.id.btn_three_module, R.id.btn_four_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one_arad:

                Intent intent = new Intent(this, OneActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_two_function:

                //TODO  这个图片选择器有BUG 可以考虑下知乎的开源版本
                Intent intent1 = new Intent(MainActivity.this, ImageSelectorMainActivity.class);
                startActivity(intent1);

                break;
            case R.id.btn_three_module:

                Intent intent2 = new Intent(MainActivity.this, GuideDemoActivity.class);
                startActivity(intent2);

                break;
            case R.id.btn_four_app:
                Intent intent3 = new Intent(MainActivity.this, CartDemoActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
