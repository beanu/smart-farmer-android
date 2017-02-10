package com.beanu.l2_push.receiver;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.beanu.l2_push.IPushListener;
import com.beanu.l2_push.PhoneTarget;
import com.beanu.l2_push.PushMessage;
import com.beanu.l2_push.util.PushHandler;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 小米推送 接受者,运行在非UI线程
 * Created by Beanu on 2016/12/23.
 */

public class MiMessageReceiver extends PushMessageReceiver {

    private static final String TAG = "MiuiPush";

    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    private static IPushListener mPushListener;

    public static IPushListener getPushListener() {
        return mPushListener;
    }

    public static void registerPushListener(IPushListener mPushListener) {
        MiMessageReceiver.mPushListener = mPushListener;
    }

    public static void clearPushListener() {
        mPushListener = null;
    }

    /**
     * 接收服务器推送的透传消息，消息封装在 MiPushMessage类中
     */
    @Override
    public void onReceivePassThroughMessage(final Context context, final MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        Log.d(TAG, "onReceivePassThroughMessage: " + message.toString());

        if (mPushListener != null) {
            final PushMessage result = new PushMessage();
            result.setMessageID(message.getMessageId());
            result.setTitle(message.getTitle());
            result.setMessage(message.getContent());
            result.setExtra(message.getExtra());
            result.setTarget(PhoneTarget.MIUI);

            PushHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushListener.onCustomMessage(context, result);
                }
            });
        }

    }

    /**
     * 接收服务器推送的通知消息，用户点击后触发
     */
    @Override
    public void onNotificationMessageClicked(final Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }

        Log.d(TAG, "onNotificationMessageClicked: " + message.toString());


        if (mPushListener != null) {
            final PushMessage result = new PushMessage();
            result.setNotifyID(message.getNotifyId());
            result.setMessageID(message.getMessageId());
            result.setTitle(mTopic);
            result.setMessage(mMessage);
            result.setExtra(message.getExtra());
            result.setTarget(PhoneTarget.MIUI);

            PushHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushListener.onMessageClicked(context, result);
                }
            });
        }
    }

    /**
     * 接收服务器推送的通知消息，消息到达客户端时触发，还可以接受应用在前台时不弹出通知的通知消息
     * 在MIUI上，只有应用处于启动状态，或者自启动白名单中，才可以通过此方法接受到该消息
     */
    @Override
    public void onNotificationMessageArrived(final Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }

        Log.d(TAG, "onNotificationMessageArrived: " + message.toString());

        if (mPushListener != null) {
            final PushMessage result = new PushMessage();
            result.setTitle(message.getTitle());
            result.setMessageID(message.getMessageId());
            result.setNotifyID(message.getNotifyId());
            result.setMessage(message.getDescription());
            result.setExtra(message.getExtra());
            result.setTarget(PhoneTarget.MIUI);

            PushHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushListener.onMessage(context, result);
                }
            });
        }

    }

    /**
     * 获取给服务器发送命令的结果，结果封装在MiPushCommandMessage类中
     */
    @Override
    public void onCommandResult(final Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;

                //注册
                if (mPushListener != null) {
                    PushHandler.handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mPushListener.onRegister(context, mRegId);
                        }
                    });
                }

            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;

                //别名
                if (mPushListener != null) {
                    PushHandler.handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mPushListener.onAlias(context, mAlias);
                        }
                    });
                }

            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    /**
     * 获取给服务器发送注册命令的结果，结果封装在MiPushCommandMessage类中
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
    }
}
