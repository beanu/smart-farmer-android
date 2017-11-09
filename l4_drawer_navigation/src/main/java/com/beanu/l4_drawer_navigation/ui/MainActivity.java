package com.beanu.l4_drawer_navigation.ui;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.beanu.l4_drawer_navigation.R;
import com.beanu.l4_drawer_navigation.base.DrawerActivity;

public class MainActivity extends DrawerActivity {

    private TextView mStatus;

    @Override
    protected void initView() {
        setToggleButton(getToolbar());
        mStatus = findViewById(R.id.text_status);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_page_a:
                mStatus.setText("Status A");
                break;
            case R.id.nav_page_b:
                mStatus.setText("Status B");
                break;
            case R.id.nav_page_c:
                mStatus.setText("Status C");
                break;
            case R.id.nav_setting:
            case R.id.nav_about:
                startActivity(SettingActivity.class);
                break;
        }
        closeDrawer();
        return true;
    }

    @Override
    protected void onQuit() {

    }

    @Override
    public String setupToolBarTitle() {
        return "首页";
    }
}
