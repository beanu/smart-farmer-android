package com.beanu.l4_bottom_tab.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l4_bottom_tab.R;


/**
 * 带有底部导航的首页
 * 第三方通用底部导航栏，使用时直接继承NavBarActivity实现未实现的方法
 * Created by Beanu on 16/1/26.
 */
public abstract class NavBarActivity extends ToolBarActivity implements TabHost.OnTabChangeListener {

    private long waitTime = 2000;
    private long touchTime = 0;
    FragmentTabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setBackgroundColor(getResources().getColor(R.color.tab_bg));
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(this);

        TabInfo[] tabs = createTabInfo();
        if (tabs != null) {

            for (TabInfo tabInfo : tabs) {
                mTabHost.addTab(mTabHost.newTabSpec(tabInfo.tag).setIndicator(getTabItemView(tabInfo.bitmapResId, getString(tabInfo.titleSId))),
                        tabInfo.fragment, null);
            }
        }

        mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = (int) getResources().getDimension(R.dimen.tab_height);

    }

    public FragmentTabHost getmTabHost() {
        return mTabHost;
    }

    private View getTabItemView(int id, String title) {
        View view = getLayoutInflater().inflate(R.layout.item_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(id);

        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(title);

        return view;
    }

    /***
     * 抽象方法，放入自己界面的名字和图标
     *
     * @return
     */
    protected abstract TabInfo[] createTabInfo();

    @Override
    public void onTabChanged(String tabId) {
        //DO nothing
    }

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

    public static class TabInfo {
        String tag;
        int bitmapResId;
        int titleSId;
        Class fragment;

        public TabInfo(String tag, int bitmapResId, int titleSId, Class fragment) {
            this.tag = tag;
            this.bitmapResId = bitmapResId;
            this.titleSId = titleSId;
            this.fragment = fragment;
        }
    }
}
