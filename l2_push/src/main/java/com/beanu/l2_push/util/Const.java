package com.beanu.l2_push.util;

import android.text.TextUtils;

/**
 * Created by Beanu on 2017/2/10.
 */

public class Const {

    private static String miui_app_id = null;
    private static String miui_app_key = null;

    public static String getMiui_app_id() {
        if (TextUtils.isEmpty(miui_app_id)) {
            throw new NullPointerException("please config miui_app_id before use it");
        }
        return miui_app_id;
    }

    public static String getMiui_app_key() {
        if (TextUtils.isEmpty(miui_app_key)) {
            throw new NullPointerException("please config miui_app_key before use it");
        }
        return miui_app_key;
    }


    public static void setMiUI_APP(String miui_app_id, String miui_app_key) {
        setMiui_app_id(miui_app_id);
        setMiui_app_key(miui_app_key);
    }


    private static void setMiui_app_id(String miui_app_id) {
        Const.miui_app_id = miui_app_id;
    }

    private static void setMiui_app_key(String miui_app_key) {
        Const.miui_app_key = miui_app_key;
    }


}
