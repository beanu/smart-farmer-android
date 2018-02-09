package com.beanu.l4_bottom_tab;


import com.alibaba.android.arouter.launcher.ARouter;
import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;
import com.beanu.arad.support.log.KLog;

/**
 * 全局入口
 * Created by Beanu on 16/10/17.
 */

public class L4Application extends AradApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (getApplicationContext().getPackageName().equals(processName)) {

            //Log日志
            KLog.init(BuildConfig.DEBUG);

            ARouter.init(this);

        }
    }

    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
}
