package com.beanu.l4_bottom_tab.ui;

import android.os.Bundle;

import com.beanu.arad.Arad;
import com.beanu.arad.support.updateversion.UpdateChecker;
import com.beanu.l3_common.model.bean.EventModel;
import com.beanu.l3_common.util.AppHolder;
import com.beanu.l3_common.util.Constants;
import com.beanu.l4_bottom_tab.base.NavBarActivity;
import com.beanu.l4_bottom_tab.ui.module1.Fragment1;
import com.beanu.l4_bottom_tab.ui.module2.Fragment2;
import com.beanu.l4_bottom_tab.ui.module3.Fragment3;
import com.beanu.l4_bottom_tab.ui.moudle4.Fragment4;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


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
        Arad.bus.register(this);
    }

    @Override
    protected List<Fragment> createFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());

        return fragments;
    }


    @Override
    protected void onQuit() {
//        AppHolder.getInstance().reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Arad.bus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(EventModel.LoginEvent event) {
        //接收到登陆成功事件之后，初始化 融云 极光等

    }


}
