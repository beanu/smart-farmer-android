package com.beanu.l2_push.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.beanu.l2_push.IPushListener;
import com.beanu.l2_push.PhoneTarget;
import com.beanu.l2_push.PushMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 极光推送 接收器
 */
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    private static IPushListener mPushListener;

    public static IPushListener getPushListener() {
        return mPushListener;
    }

    public static void registerPushListener(IPushListener mPushListener) {
        JPushReceiver.mPushListener = mPushListener;
    }

    public static void clearPushListener() {
        mPushListener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String messageId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        String extraMessage = bundle.getString(JPushInterface.EXTRA_EXTRA);


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

            if (mPushListener != null) {
                mPushListener.onRegister(context, regId);
            }

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + extraMessage);

            if (mPushListener != null) {
                PushMessage message = new PushMessage();
                message.setTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
                message.setMessageID(messageId);
                message.setMessage(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                message.setExtra(processExtraMessage(extraMessage));
                message.setTarget(PhoneTarget.JPUSH);
                mPushListener.onCustomMessage(context, message);
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知 其ID为: " + notifactionId);
            if (mPushListener != null) {
                PushMessage message = new PushMessage();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(processExtraMessage(extraMessage));
                message.setTarget(PhoneTarget.JPUSH);
                mPushListener.onMessage(context, message);
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            if (mPushListener != null) {
                PushMessage message = new PushMessage();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(processExtraMessage(extraMessage));
                message.setTarget(PhoneTarget.JPUSH);
                mPushListener.onMessageClicked(context, message);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private Map<String, String> processExtraMessage(String extras) {

        if (TextUtils.isEmpty(extras)) {
            Log.i(TAG, "This message has no Extra data");
            return null;
        }

        try {
            JSONObject json = new JSONObject(extras);
            Iterator<String> it = json.keys();

            Map<String, String> message = new HashMap<>();
            while (it.hasNext()) {
                String myKey = it.next().toString();
                message.put(myKey, json.optString(myKey));
            }

            return message;
        } catch (JSONException e) {
            Log.e(TAG, "Get message extra JSON error!");
            return null;
        }


    }

}
