package com.beanu.l4_clean.ui;

import android.os.Bundle;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.updateversion.UpdateChecker;
import com.beanu.l3_common.model.bean.EventModel;
import com.beanu.l3_common.util.AppHolder;
import com.beanu.l3_common.util.Constants;
import com.beanu.l4_clean.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //版本更新提示
        if (AppHolder.getInstance().mVersion.getVersion() > 0) {
            UpdateChecker.checkForDialog(MainActivity.this, AppHolder.getInstance().mVersion.getDesc(), Constants.IMAGE_URL + AppHolder.getInstance().mVersion.getUrl(), AppHolder.getInstance().mVersion.getVersion());
        }
        Arad.bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppHolder.getInstance().reset();
        Arad.bus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(EventModel.LoginEvent event) {
        //接收到登陆成功事件之后，初始化 融云 极光等

    }

    @Override
    public String setupToolBarTitle() {
        return "首页";
    }
}
