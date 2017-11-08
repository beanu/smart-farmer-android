package com.beanu.sf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.beanu.sf.MainListFragment;

/**
 * 直播课详情  页面
 * Created by Beanu on 2017/3/7.
 */

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    String[] mTitleList;

    public MainFragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {

        return MainListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitleList.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];
    }


}
