package com.beanu.l2_shareutil.demo;

import android.content.Context;
import android.widget.Toast;

import com.beanu.l2_shareutil.login.LoginListener;
import com.beanu.l2_shareutil.login.LoginPlatform;
import com.beanu.l2_shareutil.login.LoginResult;

/**
 * Created by Administrator on 2017/1/18.
 */

public class LoginListenerImpl extends LoginListener {

    private Context mContext;
    private String sharePlatformName = "";

    public LoginListenerImpl(Context mContext, int sharePlatform) {
        this.mContext = mContext;

        switch (sharePlatform) {
            case LoginPlatform.QQ:
                sharePlatformName = "QQ";
                break;
            case LoginPlatform.WX:
                sharePlatformName = "微信";
                break;
            case LoginPlatform.WEIBO:
                sharePlatformName = "微博";
                break;
        }
    }

    @Override
    public void loginSuccess(LoginResult result) {
        Toast.makeText(mContext, sharePlatformName + "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailure(Exception e) {
        e.printStackTrace();
        Toast.makeText(mContext, sharePlatformName + "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginCancel() {
        Toast.makeText(mContext, sharePlatformName + "登录取消", Toast.LENGTH_SHORT).show();
    }
}
