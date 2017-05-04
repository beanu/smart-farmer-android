package com.beanu.sf.ui.layer2.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beanu.sf.R;
import com.beanu.sf.ui.layer2.recycleview.classic.DemoClassicPTRActivity;
import com.beanu.sf.ui.layer2.recycleview.customAnim.DemoAnimPTRActivity;
import com.beanu.sf.ui.layer2.recycleview.loadmore.DemoLoadMoreActivity;
import com.beanu.sf.ui.layer2.recycleview.loadmore_header.DemoHeaderLoadMoreActivity;
import com.beanu.sf.ui.layer2.recycleview.simplest.DemoSimplestActivity;
import com.beanu.sf.ui.layer2.recycleview.storehouse.StoreHouseActivity;

public class RecycleViewDemoActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_demo);

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
        if (id == R.id.btn1) {
            Intent intent = new Intent(this, DemoClassicPTRActivity.class);
            startActivity(intent);

        } else if (id == R.id.btn2) {
            Intent intent2 = new Intent(this, StoreHouseActivity.class);
            startActivity(intent2);

        } else if (id == R.id.btn3) {
            Intent intent3 = new Intent(this, DemoAnimPTRActivity.class);
            startActivity(intent3);

        } else if (id == R.id.btn4) {
            Intent intent4 = new Intent(this, DemoLoadMoreActivity.class);
            startActivity(intent4);

        } else if (id == R.id.btn5) {
            Intent intent5 = new Intent(this, DemoHeaderLoadMoreActivity.class);
            startActivity(intent5);

        } else if (id == R.id.btn6) {
            Intent intent6 = new Intent(this, DemoSimplestActivity.class);
            startActivity(intent6);

        }

    }
}
