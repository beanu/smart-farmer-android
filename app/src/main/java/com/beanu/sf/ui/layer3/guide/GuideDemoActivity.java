package com.beanu.sf.ui.layer3.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.beanu.l3_guide.common.GuideActivity;
import com.beanu.l3_guide.video.GuideVideoActivity;
import com.beanu.sf.R;

public class GuideDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStyle1;
    private Button mBtnStyle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_guide);

        mBtnStyle1 = (Button) findViewById(R.id.btn_style1);
        mBtnStyle2 = (Button) findViewById(R.id.btn_style2);

        mBtnStyle1.setOnClickListener(this);
        mBtnStyle2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_style1) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_style2) {
            Intent intent = new Intent(this, GuideVideoActivity.class);
            startActivity(intent);
        }
    }
}
