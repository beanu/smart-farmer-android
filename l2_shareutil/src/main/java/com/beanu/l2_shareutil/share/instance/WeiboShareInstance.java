package com.beanu.l2_shareutil.share.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;

import com.beanu.l2_shareutil.ShareUtil;
import com.beanu.l2_shareutil.share.ImageDecoder;
import com.beanu.l2_shareutil.share.ShareImageObject;
import com.beanu.l2_shareutil.share.ShareListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboResponse;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by shaohui on 2016/11/18.
 */

public class WeiboShareInstance implements ShareInstance {
    /**
     * 微博分享限制thumb image必须小于2097152，否则点击分享会没有反应
     */

    private IWeiboShareAPI mWeiboShareAPI;

    private static final int TARGET_SIZE = 1024;

    private static final int TARGET_LENGTH = 2097152;

    public WeiboShareInstance(Context context, String appId) {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, appId);
        mWeiboShareAPI.registerApp();
    }

    @Override
    public void shareText(int platform, String text, Activity activity, ShareListener listener) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.textObject = textObject;

        sendRequest(activity, message);
    }

    @Override
    public void shareMedia(int platform, final String title, final String targetUrl, String summary,
                           ShareImageObject shareImageObject, final Activity activity,
                           final ShareListener listener) {
        String content = String.format("%s %s", title, targetUrl);
        shareTextOrImage(shareImageObject, content, activity, listener);
    }

    @Override
    public void shareImage(int platform, ShareImageObject shareImageObject, Activity activity,
                           ShareListener listener) {
        shareTextOrImage(shareImageObject, null, activity, listener);
    }

    @Override
    public void handleResult(Intent intent) {
        SendMessageToWeiboResponse baseResponse =
                new SendMessageToWeiboResponse(intent.getExtras());

        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                ShareUtil.mShareListener.shareSuccess();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                ShareUtil.mShareListener.shareFailure(new Exception(baseResponse.errMsg));
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                ShareUtil.mShareListener.shareCancel();
                break;
            default:
                ShareUtil.mShareListener.shareFailure(new Exception(baseResponse.errMsg));
        }
    }

    @Override
    public boolean isInstall(Context context) {
        return mWeiboShareAPI.isWeiboAppInstalled();
    }

    @Override
    public void recycle() {
        mWeiboShareAPI = null;
    }

    private void shareTextOrImage(final ShareImageObject shareImageObject, final String text,
            final Activity activity, final ShareListener listener) {

        Flowable.create(new FlowableOnSubscribe<Pair<String, byte[]>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Pair<String, byte[]>> emitter) throws Exception {
                try {
                    String path = ImageDecoder.decode(activity, shareImageObject);
                    emitter.onNext(Pair.create(path,
                            ImageDecoder.compress2Byte(path, TARGET_SIZE, TARGET_LENGTH)));
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(new LongConsumer() {
                    @Override
                    public void accept(long aLong) {
                        listener.shareRequest();
                    }
                })
                .subscribe(new Consumer<Pair<String,byte[]>>() {
                    @Override
                    public void accept(Pair<String, byte[]> pair) {
                        ImageObject imageObject = new ImageObject();
                        imageObject.imageData = pair.second;
                        imageObject.imagePath = pair.first;

                        WeiboMultiMessage message = new WeiboMultiMessage();
                        message.imageObject = imageObject;
                        if (!TextUtils.isEmpty(text)) {
                            TextObject textObject = new TextObject();
                            textObject.text = text;

                            message.textObject = textObject;
                        }

                        sendRequest(activity, message);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        activity.finish();
                        listener.shareFailure(new Exception(throwable));
                    }
                });
    }

    private void sendRequest(Activity activity, WeiboMultiMessage message) {
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = message;
        mWeiboShareAPI.sendRequest(activity, request);
    }
}
