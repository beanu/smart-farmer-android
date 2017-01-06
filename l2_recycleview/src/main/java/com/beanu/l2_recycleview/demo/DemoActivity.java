package com.beanu.l2_recycleview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beanu.l2_recycleview.R;
import com.beanu.l2_recycleview.demo.classic.DemoClassicPTRActivity;
import com.beanu.l2_recycleview.demo.customAnim.DemoAnimPTRActivity;
import com.beanu.l2_recycleview.demo.loadmore.DemoLoadMoreActivity;
import com.beanu.l2_recycleview.demo.loadmore_header.DemoHeaderLoadMoreActivity;
import com.beanu.l2_recycleview.demo.simplest.DemoSimplestActivity;
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
        findViewById(R.id.btn6).setOnClickListener(this);
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
                Intent intent4 = new Intent(this, DemoLoadMoreActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn5:
                Intent intent5 = new Intent(this, DemoHeaderLoadMoreActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn6:
                Intent intent6 = new Intent(this, DemoSimplestActivity.class);
                startActivity(intent6);
                break;
        }

    }
}
