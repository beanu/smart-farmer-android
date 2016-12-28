package com.beanu.l4_clean.ui;

import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l4_clean.R;

public class MainActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public String setupToolBarTitle() {
        return "首页";
    }
}
