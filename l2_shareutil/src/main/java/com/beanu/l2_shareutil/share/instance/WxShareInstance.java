package com.beanu.l2_shareutil.share.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.beanu.l2_shareutil.ShareUtil;
import com.beanu.l2_shareutil.share.ImageDecoder;
import com.beanu.l2_shareutil.share.ShareImageObject;
import com.beanu.l2_shareutil.share.ShareListener;
import com.beanu.l2_shareutil.share.SharePlatform;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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

public class WxShareInstance implements ShareInstance {

    /**
     * 微信分享限制thumb image必须小于32Kb，否则点击分享会没有反应
     */

    private IWXAPI mIWXAPI;

    private static final int THUMB_SIZE = 32 * 1024 * 8;

    private static final int TARGET_SIZE = 200;

    public WxShareInstance(Context context, String appId) {
        mIWXAPI = WXAPIFactory.createWXAPI(context, appId, true);
        mIWXAPI.registerApp(appId);
    }

    @Override
    public void shareText(int platform, String text, Activity activity, ShareListener listener) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = text;

        sendMessage(platform, message, buildTransaction("text"));
    }

    @Override
    public void shareMedia(
            final int platform, final String title, final String targetUrl, final String summary,
            final ShareImageObject shareImageObject, final Activity activity, final ShareListener listener) {
        Flowable.create(new FlowableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<byte[]> emitter) throws Exception {
                try {
                    String imagePath = ImageDecoder.decode(activity, shareImageObject);
                    emitter.onNext(ImageDecoder.compress2Byte(imagePath, TARGET_SIZE, THUMB_SIZE));
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
                .subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) {
                        WXWebpageObject webpageObject = new WXWebpageObject();
                        webpageObject.webpageUrl = targetUrl;

                        WXMediaMessage message = new WXMediaMessage(webpageObject);
                        message.title = title;
                        message.description = summary;
                        message.thumbData = bytes;

                        sendMessage(platform, message, buildTransaction("webPage"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        activity.finish();
                        listener.shareFailure(new Exception(throwable));
                    }
                });
    }

    @Override
    public void shareImage(final int platform, final ShareImageObject shareImageObject,
            final Activity activity, final ShareListener listener) {
        Flowable.create(new FlowableOnSubscribe<Pair<Bitmap, byte[]>>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Pair<Bitmap, byte[]>> emitter) throws Exception {
                try {
                    String imagePath = ImageDecoder.decode(activity, shareImageObject);
                    emitter.onNext(Pair.create(BitmapFactory.decodeFile(imagePath),
                            ImageDecoder.compress2Byte(imagePath, TARGET_SIZE, THUMB_SIZE)));
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnRequest(new LongConsumer() {
                    @Override
                    public void accept(long aLong) {
                        listener.shareRequest();
                    }
                })
                .subscribe(new Consumer<Pair<Bitmap,byte[]>>() {
                    @Override
                    public void accept(Pair<Bitmap, byte[]> pair) {
                        WXImageObject imageObject = new WXImageObject(pair.first);

                        WXMediaMessage message = new WXMediaMessage();
                        message.mediaObject = imageObject;
                        message.thumbData = pair.second;

                        sendMessage(platform, message, buildTransaction("image"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        activity.finish();
                        listener.shareFailure(new Exception(throwable));
                    }
                });
    }

    @Override
    public void handleResult(Intent data) {
        mIWXAPI.handleIntent(data, new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {
            }

            @Override
            public void onResp(BaseResp baseResp) {
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        ShareUtil.mShareListener.shareSuccess();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        ShareUtil.mShareListener.shareCancel();
                        break;
                    default:
                        ShareUtil.mShareListener.shareFailure(new Exception(baseResp.errStr));
                }
            }
        });
    }

    @Override
    public boolean isInstall(Context context) {
        return mIWXAPI.isWXAppInstalled();
    }

    @Override
    public void recycle() {
        mIWXAPI.detach();
    }

    private void sendMessage(int platform, WXMediaMessage message, String transaction) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = message;
        req.scene = platform == SharePlatform.WX_TIMELINE ? SendMessageToWX.Req.WXSceneTimeline
                : SendMessageToWX.Req.WXSceneSession;
        mIWXAPI.sendReq(req);
    }

    private String buildTransaction(String type) {
        return System.currentTimeMillis() + type;
    }

}
