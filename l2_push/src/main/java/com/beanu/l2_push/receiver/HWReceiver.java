package com.beanu.l2_push.receiver;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.beanu.l2_push.IPushListener;
import com.beanu.l2_push.PhoneTarget;
import com.beanu.l2_push.PushMessage;
import com.beanu.l2_push.util.PushHandler;
import com.huawei.android.pushagent.api.PushEventReceiver;

/*
 * 接收华为 Push所有消息的广播接收器
 */
public class HWReceiver extends PushEventReceiver {

    private static final String TAG = "HWPush";

    private static String mToken = null;
    private static IPushListener mPushListener;

    public static IPushListener getPushListener() {
        return mPushListener;
    }

    public static void registerPushListener(IPushListener mPushListener) {
        HWReceiver.mPushListener = mPushListener;
    }

    public static void clearPushListener() {
        mPushListener = null;
    }

    public static String getmToken() {
        return mToken;
    }

    @Override
    public void onToken(final Context context, final String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Log.d(TAG, content);
        mToken = token;
        if (mPushListener != null) {
            PushHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushListener.onRegister(context, token);
                }
            });
        }
    }

    /**
     * 接收透传消息
     * bundle 暂时不启用
     */
    @Override
    public boolean onPushMsg(final Context context, byte[] msg, Bundle bundle) {

        Log.d(TAG, "onPushMsg: " + new String(msg));

        try {
            String content = new String(msg, "UTF-8");
            if (mPushListener != null) {
                final PushMessage message = new PushMessage();
                message.setMessage(content);
                //华为的sdk在透传的时候无法实现extra字段，这里要注意
                message.setExtra(null);
                message.setTarget(PhoneTarget.EMUI);
                PushHandler.handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mPushListener.onCustomMessage(context, message);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * TODO 继续完善
     * 在华为的sdk中，通知栏的事件只有三种:
     * <p>
     * NOTIFICATION_OPENED, //通知栏中的通知被点击打开
     * NOTIFICATION_CLICK_BTN, //通知栏中通知上的按钮被点击
     * PLUGINRSP, //标签上报回应
     */
    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            Log.d(TAG, content);
//            showPushMessage(PustDemoActivity.RECEIVE_NOTIFY_CLICK_MSG, content);
        } else if (Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if (TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            Log.d(TAG, message + isSuccess);
//            showPushMessage(PustDemoActivity.RECEIVE_TAG_LBS_MSG, message + isSuccess);
        }
        super.onEvent(context, event, extras);
    }
}
