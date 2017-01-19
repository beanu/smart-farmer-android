package com.beanu.l2_shareutil.demo;

import android.content.Context;
import android.widget.Toast;

import com.beanu.l2_shareutil.share.ShareListener;
import com.beanu.l2_shareutil.share.SharePlatform;

/**
 * Created by Lotte on 2017/1/18.
 */

public class ShareListenerImpl extends ShareListener {

    private Context mContext;
    private String sharePlatformName = "";

    public ShareListenerImpl(Context mContext, int sharePlatform) {
        this.mContext = mContext;

        switch (sharePlatform) {
            case SharePlatform.QQ:
                sharePlatformName = "QQ";
                break;
            case SharePlatform.QZONE:
                sharePlatformName = "QQ空间";
                break;
            case SharePlatform.WX:
                sharePlatformName = "微信";
                break;
            case SharePlatform.WX_TIMELINE:
                sharePlatformName = "朋友圈";
                break;
            case SharePlatform.WEIBO:
                sharePlatformName = "微博";
                break;
        }
    }

    @Override
    public void shareSuccess() {
        Toast.makeText(mContext, sharePlatformName + "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareFailure(Exception e) {
        Toast.makeText(mContext, sharePlatformName + "分享失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareCancel() {
        Toast.makeText(mContext, sharePlatformName + "分享取消", Toast.LENGTH_SHORT).show();
    }
}
