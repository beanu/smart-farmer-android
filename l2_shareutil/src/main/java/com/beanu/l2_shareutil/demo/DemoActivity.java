package com.beanu.l2_shareutil.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beanu.l2_shareutil.LoginUtil;
import com.beanu.l2_shareutil.R;
import com.beanu.l2_shareutil.ShareUtil;
import com.beanu.l2_shareutil.login.LoginPlatform;
import com.beanu.l2_shareutil.login.LoginResult;
import com.beanu.l2_shareutil.share.SharePlatform;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnQq, mBtnQqImage, mBtnQqMedia;
    private Button mBtnWeixin, mBtnWeixinImage, mBtnWeixinMedia, mBtnWeixinCircleMedia;
    private Button mBtnWeibo, mBtnWeiboImage, mBtnWeiboMedia;
    private Button mBtnQqLogin, mBtnWeixinLogin, mBtnWeiBoLogin;
    private TextView mTvResult;

    private String shareTitle = "分享的标题";
    private String shareSummary = "分享的摘要";
    private String shareUrl = "http://www.baidu.com";
    private String shareImgUrl = "http://of9v68w5i.bkt.clouddn.com/103ce63064dc44dbb969ddde3e9a9be1";

    /**
     * 登录成功后,是否获取用户信息
     */
    private boolean isFetchUserInfo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mBtnQq = (Button) findViewById(R.id.btn_qq);
        mBtnQqImage = (Button) findViewById(R.id.btn_qq_image);
        mBtnQqMedia = (Button) findViewById(R.id.btn_qq_media);
        mBtnWeixin = (Button) findViewById(R.id.btn_weixin);
        mBtnWeixinImage = (Button) findViewById(R.id.btn_weixin_image);
        mBtnWeixinMedia = (Button) findViewById(R.id.btn_weixin_media);
        mBtnWeixinCircleMedia = (Button) findViewById(R.id.btn_weixin_circle_media);
        mBtnWeibo = (Button) findViewById(R.id.btn_weibo);
        mBtnWeiboImage = (Button) findViewById(R.id.btn_weibo_image);
        mBtnWeiboMedia = (Button) findViewById(R.id.btn_weibo_media);
        mBtnQqLogin = (Button) findViewById(R.id.btn_qq_login);
        mBtnWeixinLogin = (Button) findViewById(R.id.btn_weixin_login);
        mBtnWeiBoLogin = (Button) findViewById(R.id.btn_weibo_login);
        mTvResult = (TextView) findViewById(R.id.tv_result);

        mBtnQq.setOnClickListener(this);
        mBtnQqImage.setOnClickListener(this);
        mBtnQqMedia.setOnClickListener(this);
        mBtnWeixin.setOnClickListener(this);
        mBtnWeixinImage.setOnClickListener(this);
        mBtnWeixinMedia.setOnClickListener(this);
        mBtnWeixinCircleMedia.setOnClickListener(this);
        mBtnWeibo.setOnClickListener(this);
        mBtnWeiboImage.setOnClickListener(this);
        mBtnWeiboMedia.setOnClickListener(this);
        mBtnQqLogin.setOnClickListener(this);
        mBtnWeixinLogin.setOnClickListener(this);
        mBtnWeiBoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int i = v.getId();
        if (i == R.id.btn_qq) {
            ShareUtil.shareMedia(this, SharePlatform.QQ, shareTitle, shareSummary, shareUrl,
                    shareImgUrl, new ShareListenerImpl(this, SharePlatform.QQ));

        } else if (i == R.id.btn_qq_image) {
            ShareUtil.shareImage(this, SharePlatform.QQ, shareImgUrl, new ShareListenerImpl(this, SharePlatform.QQ));

        } else if (i == R.id.btn_qq_media) {
            ShareUtil.shareMedia(this, SharePlatform.QQ, shareTitle, shareSummary, shareUrl,
                    bitmap, new ShareListenerImpl(this, SharePlatform.QQ));

        } else if (i == R.id.btn_weixin) {
            ShareUtil.shareMedia(this, SharePlatform.WX, shareTitle, shareSummary, shareUrl,
                    shareImgUrl, new ShareListenerImpl(this, SharePlatform.WX));

        } else if (i == R.id.btn_weixin_image) {
            ShareUtil.shareImage(this, SharePlatform.WX, shareImgUrl, new ShareListenerImpl(this, SharePlatform.WX));

        } else if (i == R.id.btn_weixin_media) {
            ShareUtil.shareMedia(this, SharePlatform.WX, shareTitle, shareSummary, shareUrl,
                    bitmap, new ShareListenerImpl(this, SharePlatform.WX));

        } else if (i == R.id.btn_weixin_circle_media) {
            ShareUtil.shareMedia(this, SharePlatform.WX_TIMELINE, shareTitle, shareSummary, shareUrl,
                    bitmap, new ShareListenerImpl(this, SharePlatform.WX_TIMELINE));

        } else if (i == R.id.btn_weibo) {
            if (!ShareUtil.isWeiBoInstalled(this)) {//是否安装微博
                Toast.makeText(this, "没有安装微博客户端", Toast.LENGTH_SHORT).show();
            } else {
                ShareUtil.shareMedia(this, SharePlatform.WEIBO, shareTitle, shareSummary, shareUrl,
                        shareImgUrl, new ShareListenerImpl(this, SharePlatform.WEIBO));
            }

        } else if (i == R.id.btn_weibo_image) {//                ShareUtil.shareImage(this, SharePlatform.WEIBO, shareImgUrl, new ShareListenerImpl(this, SharePlatform.WEIBO));
            ShareUtil.shareImage(this, SharePlatform.WEIBO, bitmap, new ShareListenerImpl(this, SharePlatform.WEIBO));

        } else if (i == R.id.btn_weibo_media) {
            ShareUtil.shareMedia(this, SharePlatform.WEIBO, shareTitle, shareSummary, shareUrl,
                    bitmap, new ShareListenerImpl(this, SharePlatform.WEIBO));


            //第三方登录,登录成功后显示登录后得到的数据
        } else if (i == R.id.btn_qq_login) {
            LoginUtil.login(this, LoginPlatform.QQ, new LoginListenerImpl(this, LoginPlatform.QQ) {
                @Override
                public void loginSuccess(LoginResult result) {
                    super.loginSuccess(result);
                    setLoginResutToView(result, "QQ");
                }
            }, isFetchUserInfo);

        } else if (i == R.id.btn_weixin_login) {
            LoginUtil.login(this, LoginPlatform.WX, new LoginListenerImpl(this, LoginPlatform.WX) {
                @Override
                public void loginSuccess(LoginResult result) {
                    super.loginSuccess(result);
                    setLoginResutToView(result, "微信");
                }
            }, isFetchUserInfo);

        } else if (i == R.id.btn_weibo_login) {
            LoginUtil.login(this, LoginPlatform.WEIBO, new LoginListenerImpl(this, LoginPlatform.WEIBO) {
                @Override
                public void loginSuccess(LoginResult result) {
                    super.loginSuccess(result);
                    setLoginResutToView(result, "微博");
                }
            }, isFetchUserInfo);

        }
    }

    //显示登录后得到的数据
    private void setLoginResutToView(LoginResult result, String platformName) {
        if (null != result) {
            mTvResult.setText(platformName + "登录结果:" + result.toString());
        } else {
            Toast.makeText(DemoActivity.this, platformName + "登录结果为空", Toast.LENGTH_SHORT).show();
        }
    }
}
