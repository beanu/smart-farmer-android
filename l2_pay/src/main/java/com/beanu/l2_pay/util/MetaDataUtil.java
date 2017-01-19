package com.beanu.l2_pay.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.support.annotation.Nullable;

/**
 * Created by lizhihua on 2017/1/18.
 */

public class MetaDataUtil {

    @Nullable
    public static String getAppMetaData(Context context, String dataName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(dataName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getActivityMetaData(Activity context, String dataName) {
        try {
            ActivityInfo info = context.getPackageManager()
                    .getActivityInfo(context.getComponentName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(dataName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getServiceMetaData(Context context, Class serviceClass, String dataName) {
        ComponentName cn = new ComponentName(context, serviceClass);
        try {
            ServiceInfo info = context.getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(dataName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getReceiverMetaData(Context context, Class receiverClass, String dataName) {
        ComponentName cn = new ComponentName(context, receiverClass);
        try {
            ActivityInfo info = context.getPackageManager()
                    .getReceiverInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(dataName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
