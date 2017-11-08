package com.beanu.sf;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.sf.adapter.MainFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */
public class MainActivity extends ToolBarActivity {

    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    @BindView(R.id.viewPager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String[] titles = new String[]{"第一层", "第二层", "第三层", "第四层"};

        MainFragmentAdapter mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public String setupToolBarTitle() {
        return "Smart Farmer";
    }
}
