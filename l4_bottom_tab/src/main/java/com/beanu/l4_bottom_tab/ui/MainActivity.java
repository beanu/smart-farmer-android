package com.beanu.l4_bottom_tab.ui;

import android.os.Bundle;

import com.beanu.arad.support.updateversion.UpdateChecker;
import com.beanu.l4_bottom_tab.R;
import com.beanu.l4_bottom_tab.base.NavBarActivity;
import com.beanu.l4_bottom_tab.ui.module1.Fragment1;
import com.beanu.l4_bottom_tab.ui.module2.Fragment2;
import com.beanu.l4_bottom_tab.ui.module3.Fragment3;
import com.beanu.l4_bottom_tab.ui.moudle4.Fragment4;
import com.beanu.l4_bottom_tab.util.AppHolder;
import com.beanu.l4_bottom_tab.util.Constants;


public class MainActivity extends NavBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bug snag 初始化
//        Bugsnag.init(this);

        //版本更新提示
        if (AppHolder.getInstance().mVersion.getVersion() > 0) {
            UpdateChecker.checkForDialog(MainActivity.this, AppHolder.getInstance().mVersion.getDesc(), Constants.IMAGE_URL + AppHolder.getInstance().mVersion.getUrl(), AppHolder.getInstance().mVersion.getVersion());
        }
    }

    @Override
    protected TabInfo[] createTabInfo() {
        TabInfo[] tabInfos = new TabInfo[4];//数组数为底部导航栏按钮数量
        tabInfos[0] = new TabInfo("home", R.drawable.tab_home_btn, R.string.tab_title_1, Fragment1.class);
        tabInfos[1] = new TabInfo("water", R.drawable.tab_home_btn, R.string.tab_title_2, Fragment2.class);
        tabInfos[2] = new TabInfo("news", R.drawable.tab_home_btn, R.string.tab_title_3, Fragment3.class);
        tabInfos[3] = new TabInfo("my", R.drawable.tab_home_btn, R.string.tab_title_4, Fragment4.class);

        return tabInfos;
    }

    @Override
    protected void onQuit() {

    }
}
