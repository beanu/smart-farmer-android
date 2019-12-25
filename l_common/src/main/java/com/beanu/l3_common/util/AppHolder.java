package com.beanu.l3_common.util;


import com.beanu.l3_common.model.bean.GlobalConfig;
import com.beanu.l3_common.model.bean.User;
import com.beanu.l3_common.model.bean.Version;

/**
 * 全局变量 尽量把变量固化到本地
 * Created by Beanu on 16/2/23.
 */
public class AppHolder {

    private static AppHolder instance;

    private AppHolder() {

        //初始化数据 防止null指针
        user = new User();
        mVersion = new Version();
        mConfig = new GlobalConfig();
    }

    public synchronized static AppHolder getInstance() {
        if (instance == null) {
            instance = new AppHolder();
        }
        return instance;
    }

    public User user;
    public Version mVersion;
    public GlobalConfig mConfig;
}
