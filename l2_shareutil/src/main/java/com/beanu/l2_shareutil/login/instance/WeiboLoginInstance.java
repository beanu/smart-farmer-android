package com.beanu.l2_shareutil.login.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.beanu.l2_shareutil.ShareLogger;
import com.beanu.l2_shareutil.ShareManager;
import com.beanu.l2_shareutil.login.LoginListener;
import com.beanu.l2_shareutil.login.LoginPlatform;
import com.beanu.l2_shareutil.login.LoginResult;
import com.beanu.l2_shareutil.login.result.BaseToken;
import com.beanu.l2_shareutil.login.result.WeiboToken;
import com.beanu.l2_shareutil.login.result.WeiboUser;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.beanu.l2_shareutil.ShareLogger.INFO;

/**
 * Created by shaohui on 2016/12/1.
 */

public class WeiboLoginInstance extends LoginInstance {

    private static final String USER_INFO = "https://api.weibo.com/2/users/show.json";

    private SsoHandler mSsoHandler;

    private LoginListener mLoginListener;

    public WeiboLoginInstance(Activity activity, LoginListener listener, boolean fetchUserInfo) {
        super(activity, listener, fetchUserInfo);
        AuthInfo authInfo = new AuthInfo(activity, ShareManager.CONFIG.getWeiboId(),
                ShareManager.CONFIG.getWeiboRedirectUrl(), ShareManager.CONFIG.getWeiboScope());
        mSsoHandler = new SsoHandler(activity, authInfo);
        mLoginListener = listener;
    }

    @Override
    public void doLogin(Activity activity, final LoginListener listener,
                        final boolean fetchUserInfo) {
        mSsoHandler.authorize(new WeiboAuthListener() {
            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
                WeiboToken weiboToken = WeiboToken.parse(accessToken);
                if (fetchUserInfo) {
                    listener.beforeFetchUserInfo(weiboToken);
                    fetchUserInfo(weiboToken);
                } else {
                    listener.loginSuccess(new LoginResult(LoginPlatform.WEIBO, weiboToken));
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ShareLogger.i(INFO.WEIBO_AUTH_ERROR);
                listener.loginFailure(e);
            }

            @Override
            public void onCancel() {
                ShareLogger.i(INFO.AUTH_CANCEL);
                listener.loginCancel();
            }
        });
    }

    @Override
    public void fetchUserInfo(final BaseToken token) {
        Flowable.create(new FlowableOnSubscribe<WeiboUser>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<WeiboUser> weiboUserEmitter) throws Exception {
                OkHttpClient client = new OkHttpClient();
                Request request =
                        new Request.Builder().url(buildUserInfoUrl(token, USER_INFO)).build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    WeiboUser user = WeiboUser.parse(jsonObject);
                    weiboUserEmitter.onNext(user);
                } catch (IOException | JSONException e) {
                    ShareLogger.e(INFO.FETCH_USER_INOF_ERROR);
                    weiboUserEmitter.onError(e);
                }
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeiboUser>() {
                    @Override
                    public void accept(WeiboUser weiboUser) {
                        mLoginListener.loginSuccess(
                                new LoginResult(LoginPlatform.WEIBO, token, weiboUser));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mLoginListener.loginFailure(new Exception(throwable));
                    }
                });
    }

    private String buildUserInfoUrl(BaseToken token, String baseUrl) {
        return baseUrl + "?access_token=" + token.getAccessToken() + "&uid=" + token.getOpenid();
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    @Override
    public boolean isInstall(Context context) {
        IWeiboShareAPI shareAPI =
                WeiboShareSDK.createWeiboAPI(context, ShareManager.CONFIG.getWeiboId());
        return shareAPI.isWeiboAppInstalled();
    }

    @Override
    public void recycle() {
        mSsoHandler = null;
        mLoginListener = null;
    }
}
