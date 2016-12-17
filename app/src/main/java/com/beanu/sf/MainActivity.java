package com.beanu.sf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.sf.ui.layer1.OneActivity;

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

                break;
            case R.id.btn_three_module:

                break;
            case R.id.btn_four_app:

                break;
        }
    }
}
