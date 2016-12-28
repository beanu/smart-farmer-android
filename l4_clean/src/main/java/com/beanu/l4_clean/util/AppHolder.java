package com.beanu.l4_clean.util;


import com.beanu.arad.Arad;
import com.beanu.arad.utils.StreamUtil;
import com.beanu.l4_clean.model.bean.User;
import com.beanu.l4_clean.model.bean.Version;

import java.io.Serializable;

/**
 * 全局变量  能够在内存不足的时候 从本地缓存获取
 * Created by Beanu on 16/2/23.
 */
public class AppHolder implements Serializable, Cloneable {

    public final static String TAG = "/SaveInstance";
    private static final long serialVersionUID = 1L;

    private static AppHolder instance;

    private AppHolder() {

        //初始化数据 防止null指针
        user = new User();
        mVersion = new Version();
    }

    public static AppHolder getInstance() {
        if (instance == null) {

            Object obj = StreamUtil.restoreObject(Arad.app.getCacheDir().getAbsolutePath() + TAG);
            if (null == obj) {
                obj = new AppHolder();
                StreamUtil.saveObject(Arad.app.getCacheDir().getAbsolutePath() + TAG, obj);
            }
            instance = (AppHolder) obj;
        }
        return instance;
    }

    private void save() {
        StreamUtil.saveObject(Arad.app.getCacheDir().getAbsolutePath() + TAG, this);
    }

    // App退出的时候，清空本地存储的对象，否则下次使用的时候还会存有上次遗留的数据
    public void reset() {
        this.user = new User();
        this.mVersion = new Version();
        save();
    }


    public void setUser(User user) {
        this.user = user;
        save();
    }

    public void setVersion(Version version) {
        mVersion = version;
        save();

    }


    public User user;
    public Version mVersion;
}
