package com.beanu.l4_bottom_tab;


import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;

/**
 * 全局入口
 * Created by Beanu on 16/10/17.
 */

public class L4Application extends AradApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        disableCrashHandler();
    }

    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
}
