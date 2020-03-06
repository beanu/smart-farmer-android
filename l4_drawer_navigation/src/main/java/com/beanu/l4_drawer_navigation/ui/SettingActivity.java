package com.beanu.l4_drawer_navigation.ui;

import android.os.Bundle;

import com.beanu.l4_drawer_navigation.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class SettingActivity extends QMUIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initTopBar(QMUITopBarLayout topBarLayout) {
        topBarLayout.setTitle("Setting");
    }

//    @Override
//    public String setupToolBarTitle() {
//        return "Setting";
//    }
//
//    @Override
//    public boolean setupToolBarLeftButton(View leftButton) {
//        leftButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        return true;
//    }
}
