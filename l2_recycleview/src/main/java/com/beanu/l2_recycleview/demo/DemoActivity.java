package com.beanu.l2_recycleview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beanu.arad.utils.MessageUtils;
import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.classic.DemoClassicPTRActivity;
import com.beanu.l2_recycleview.demo.customAnim.DemoAnimPTRActivity;
import com.beanu.l2_recycleview.demo.storehouse.StoreHouseActivity;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {


        int id = view.getId();
        switch (id) {
            case R.id.btn1:
                Intent intent = new Intent(this, DemoClassicPTRActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(this, StoreHouseActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn3:
                Intent intent3 = new Intent(this, DemoAnimPTRActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn4:
                MessageUtils.showShortToast(this, "别偷懒了，快开发");
                break;
            case R.id.btn5:
                MessageUtils.showShortToast(this, "别偷懒了，快开发");
                break;
        }

    }
}
