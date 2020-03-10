package com.beanu.sf;

import android.os.Bundle;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.sf.adapter.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */
public class MainActivity extends ToolBarActivity {

    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    @BindView(R.id.viewPager) ViewPager mViewPager;

    @BindView(R.id.toolbar_title) TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTxtTitle.setText("Smart Farmer Demo");
        QMUIStatusBarHelper.setStatusBarDarkMode(this);

        String[] titles = new String[]{"第一层", "第二层", "第三层", "第四层"};

        MainFragmentAdapter mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
