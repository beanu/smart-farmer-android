package com.beanu.l2_shareutil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.beanu.l2_shareutil.login.LoginListener;
import com.beanu.l2_shareutil.login.LoginPlatform;
import com.beanu.l2_shareutil.login.instance.LoginInstance;
import com.beanu.l2_shareutil.login.instance.QQLoginInstance;
import com.beanu.l2_shareutil.login.instance.WeiboLoginInstance;
import com.beanu.l2_shareutil.login.instance.WxLoginInstance;


/**
 * Created by shaohui on 2016/12/3.
 */

public class LoginUtil {

    private static LoginInstance mLoginInstance;

    private static LoginListener mLoginListener;

    private static int mPlatform;

    private static boolean isFetchUserInfo;

    static final int TYPE = 799;

    public static void login(Context context, @LoginPlatform.Platform int platform,
            LoginListener listener) {
        login(context, platform, listener, true);
    }

    public static void login(Context context, @LoginPlatform.Platform int platform,
            LoginListener listener, boolean fetchUserInfo) {
        mPlatform = platform;
        mLoginListener = listener;
        isFetchUserInfo = fetchUserInfo;
        if (context instanceof Application) {
            listener.doLoginFailure(new Exception("don't support the application context"));
        }
        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    static void action(Activity activity) {
        switch (mPlatform) {
            case LoginPlatform.QQ:
                mLoginInstance = new QQLoginInstance(activity, mLoginListener, isFetchUserInfo);
                break;
            case LoginPlatform.WEIBO:
                mLoginInstance = new WeiboLoginInstance(activity, mLoginListener, isFetchUserInfo);
                break;
            case LoginPlatform.WX:
                mLoginInstance = new WxLoginInstance(activity, mLoginListener, isFetchUserInfo);
                break;
        }
        mLoginInstance.doLogin(activity, mLoginListener, isFetchUserInfo);
    }

    static void handleResult(int requestCode, int resultCode, Intent data) {
        if (mLoginInstance != null) {
            mLoginInstance.handleResult(requestCode, resultCode, data);
        }
    }

    public static void recycle() {
        if (mLoginInstance != null) {
            mLoginInstance.recycle();
        }
        mLoginInstance = null;
        mLoginListener = null;
        mPlatform = 0;
        isFetchUserInfo = false;
    }
}
