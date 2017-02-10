package com.beanu.l2_push.util;

import android.os.Looper;

/**
 * 获取主Looper
 * Created by Beanu on 2017/2/10.
 */

public class PushHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (PushHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }
}
