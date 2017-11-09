package com.beanu.l4_drawer_navigation.ui;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.beanu.l4_drawer_navigation.R;
import com.beanu.l4_drawer_navigation.base.DrawerActivity;
import com.beanu.l4_drawer_navigation.ui.module1.Fragment1;
import com.beanu.l4_drawer_navigation.ui.module2.Fragment2;
import com.beanu.l4_drawer_navigation.ui.module3.Fragment3;
import com.beanu.l4_drawer_navigation.uitls.FragmentSwitcher;

public class DrawerPageActivity extends DrawerActivity implements DrawerLayout.DrawerListener {

    private FragmentSwitcher mSwitcher;
    private String currentTag;

    @Override
    protected void initView() {
        mSwitcher = new FragmentSwitcher(R.id.main_container, getSupportFragmentManager());
        mSwitcher.addFragment("fragment1", Fragment1.class)
                .addFragment("fragment2", Fragment2.class)
                .addFragment("fragment3", Fragment3.class);
        mSwitcher.switchFragment("fragment1");

        getDrawerLayout().addDrawerListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_page_a:
                currentTag = "fragment1";
                break;
            case R.id.nav_page_b:
                currentTag = "fragment2";
                break;
            case R.id.nav_page_c:
                currentTag = "fragment3";
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
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        mSwitcher.switchFragment(currentTag);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
