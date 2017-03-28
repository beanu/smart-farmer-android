package com.beanu.l3_guide.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_guide.R;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 向导页面
 */
public class GuideActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        ViewPager guide_viewpager = (ViewPager) findViewById(R.id.viewpager_guide);
        CirclePageIndicator guide_indicator = (CirclePageIndicator) findViewById(R.id.indicator_guide);

        String[] fileNames = new String[]{"guide1.png", "guide2.png"};
        GuideAdapter adapter = new GuideAdapter(getSupportFragmentManager(), fileNames);
        guide_viewpager.setAdapter(adapter);
        guide_indicator.setViewPager(guide_viewpager);
    }


    private static class GuideAdapter extends FragmentPagerAdapter {

        String[] fileNames;

        private GuideAdapter(FragmentManager fm, String[] files) {
            super(fm);
            this.fileNames = files;
        }

        @Override
        public Fragment getItem(int arg0) {
            boolean lastOne = (arg0 == (fileNames.length - 1));
            return GuideFragment.newInstance(fileNames[arg0], lastOne);
        }

        @Override
        public int getCount() {
            return fileNames.length;
        }
    }
}
