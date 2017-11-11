package com.beanu.l4_drawer_navigation.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l4_drawer_navigation.R;

/**
 * more DrawerLayout style link: https://github.com/mikepenz/MaterialDrawer
 */
public abstract class DrawerActivity extends ToolBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigation;
    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mDrawerLayout = findViewById(R.id.draw_layout);
        mNavigation = findViewById(R.id.navigation_view);
        mNavigation.setNavigationItemSelectedListener(this);
        requireDisableNextPageSlideBack();
        initView();
    }

    protected abstract void initView();

    public int getLayoutId() {
        return R.layout.activity_draw;
    }

    /**
     * 如果需要关联toolbar左侧button
     */
    protected void setToggleButton(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    protected DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    protected NavigationView getNavigation() {
        return mNavigation;
    }

    @Override
    public abstract boolean onNavigationItemSelected(@NonNull MenuItem item);


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                onQuit();
                finish();
                System.exit(0);
            }
        }
    }

    protected void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    protected abstract void onQuit();
}
