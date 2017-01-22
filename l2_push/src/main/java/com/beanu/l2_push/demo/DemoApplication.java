package com.beanu.l2_push.demo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 测试
 * Created by Beanu on 2016/12/23.
 */

public class DemoApplication extends Application {

    public static final String APP_ID = "";
    public static final String APP_KEY = "";

    public static final String TAG = "l2_push";

    @Override
    public void onCreate() {
        super.onCreate();

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        //****************小米推送 BEGIN*********************
        //1.初始化小米push推送服务
//        if (shouldInit()) {
//            MiPushClient.registerPush(this, APP_ID, APP_KEY);
//        }
//        //2.打开小米推送的Log，默认情况下，我们会将日志内容写入SDCard/Android/data/app pkgname/files/MiPushLog
//        LoggerInterface newLogger = new LoggerInterface() {
//            @Override
//            public void setTag(String tag) {
//                // ignore
//            }
//
//            @Override
//            public void log(String content, Throwable t) {
//                Log.d(TAG, content, t);
//            }
//
//            @Override
//            public void log(String content) {
//                Log.d(TAG, content);
//            }
//        };
//        Logger.setLogger(this, newLogger);
        //****************小米推送 END*********************

    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
