package com.beanu.l4_bottom_tab.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l4_bottom_tab.R;
import com.beanu.l4_bottom_tab.support.bottomnavigation.BottomNavigationViewEx;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


/**
 * 带有底部导航的首页
 * 第三方通用底部导航栏，使用时直接继承NavBarActivity实现未实现的方法
 *
 * @author Beanu
 */
public abstract class NavBarActivity extends ToolBarActivity {

    private long touchTime = 0;
    protected BottomNavigationViewEx mNavigationViewEx;
    protected FragmentStateAdapter mPagerAdapter;
    protected ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new VpAdapter(this, createFragments());

        mNavigationViewEx = findViewById(R.id.bottom_nav);
        mNavigationViewEx.enableAnimation(false);
        mNavigationViewEx.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        mNavigationViewEx.setItemHorizontalTranslationEnabled(false);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setUserInputEnabled(false);
        mViewPager.setAdapter(mPagerAdapter);


        mNavigationViewEx.setupWithViewPager(mViewPager);

    }

    /**
     * 首页fragment的创建
     *
     * @return fragment列表
     */
    protected abstract List<Fragment> createFragments();


    /**
     * 按两次返回键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 两次返回键，退出程序
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            long waitTime = 2000;
            if ((currentTime - touchTime) >= waitTime) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                onQuit();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract void onQuit();

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentStateAdapter {
        private List<Fragment> data;

        public VpAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
            super(fragmentActivity);
            this.data = fragments;
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
