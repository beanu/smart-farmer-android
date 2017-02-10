package com.beanu.l2_push;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.beanu.l2_push.receiver.HWReceiver;
import com.beanu.l2_push.receiver.JPushReceiver;
import com.beanu.l2_push.receiver.MiMessageReceiver;
import com.beanu.l2_push.util.Const;
import com.beanu.l2_push.util.RomUtil;
import com.beanu.l2_push.util.TokenModel;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 大权掌握，push 管理
 * Created by Beanu on 2017/2/10.
 */

public class PushManager {

    private static final String TAG = "PushManager";

    /**
     * 初始化配置
     */
    public static void register(Context context, boolean debug) {
        register(context, debug, null);
    }


    /**
     * 注册
     */
    public static void register(Context context, boolean debug, IPushListener pushListener) {
        if (context == null)
            return;

        if (RomUtil.rom() == PhoneTarget.EMUI) {
            if (pushListener != null) {
                HWReceiver.registerPushListener(pushListener);
            }
            com.huawei.android.pushagent.api.PushManager.requestToken(context);
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            if (pushListener != null) {
                MiMessageReceiver.registerPushListener(pushListener);
            }
            if (shouldInit(context)) {
                MiPushClient.registerPush(context, Const.getMiui_app_id(), Const.getMiui_app_key());
            }
            if (debug) {
                LoggerInterface newLogger = new LoggerInterface() {
                    @Override
                    public void setTag(String tag) {
                        // ignore
                    }

                    @Override
                    public void log(String content, Throwable t) {
                        Log.i(TAG, "content" + content + " exception: " + t.toString());
                    }

                    @Override
                    public void log(String content) {
                        Log.i(TAG, "miui: " + content);
                    }
                };
                Logger.setLogger(context, newLogger);
            }

            return;
        }


        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            if (pushListener != null) {
                JPushReceiver.registerPushListener(pushListener);
            }
            JPushInterface.init(context);
            JPushInterface.setDebugMode(debug);
        }


    }

    /**
     * 用于小米推送的注册
     */
    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void unregister(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            HWReceiver.clearPushListener();
            com.huawei.android.pushagent.api.PushManager.deregisterToken(context, getToken(context).getToken());
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiMessageReceiver.clearPushListener();
            MiPushClient.unregisterPush(context);
            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            JPushReceiver.clearPushListener();
            JPushInterface.stopPush(context);
            return;
        }
    }


    public static void setPushInterface(IPushListener pushListener) {

        if (pushListener == null)
            return;


        if (RomUtil.rom() == PhoneTarget.EMUI) {
            HWReceiver.registerPushListener(pushListener);
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiMessageReceiver.registerPushListener(pushListener);
            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            JPushReceiver.registerPushListener(pushListener);
        }
    }


    /**
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     */
    public static void setAlias(final Context context, String alias) {
        if (TextUtils.isEmpty(alias))
            return;
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            Map<String, String> tag = new HashMap<>();
            tag.put("name", alias);
            com.huawei.android.pushagent.api.PushManager.setTags(context, tag);
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiPushClient.setAlias(context, alias, null);

            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            JPushInterface.setAlias(context, alias, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    if (i == 0) { // 这里极光规定0代表成功
                        if (JPushReceiver.getPushListener() != null) {
                            JPushReceiver.getPushListener().onAlias(context, s);
                        }
                    }
                }
            });
        }

    }

    /**
     * 获取唯一的token
     *
     * @param context
     * @return
     */
    public static TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            result.setToken(HWReceiver.getmToken());
        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            result.setToken(MiPushClient.getRegId(context));
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            result.setToken(JPushInterface.getRegistrationID(context));
        }
        return result;

    }


    /**
     * 停止推送
     */
    public static void pause(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            com.huawei.android.pushagent.api.PushManager.enableReceiveNormalMsg(context, false);
            com.huawei.android.pushagent.api.PushManager.enableReceiveNotifyMsg(context, false);
            if (HWReceiver.getPushListener() != null) {
                HWReceiver.getPushListener().onPaused(context);
            }
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiPushClient.pausePush(context, null);
            if (MiMessageReceiver.getPushListener() != null) {
                MiMessageReceiver.getPushListener().onPaused(context);
            }
            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            if (!JPushInterface.isPushStopped(context)) {
                JPushInterface.stopPush(context);
                if (JPushReceiver.getPushListener() != null) {
                    JPushReceiver.getPushListener().onPaused(context);
                }
            }
        }

    }


    /**
     * 开始推送
     */
    public static void resume(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            com.huawei.android.pushagent.api.PushManager.enableReceiveNormalMsg(context, true);
            com.huawei.android.pushagent.api.PushManager.enableReceiveNotifyMsg(context, true);
            if (HWReceiver.getPushListener() != null) {
                HWReceiver.getPushListener().onResume(context);
            }
            return;
        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiPushClient.resumePush(context, null);
            if (MiMessageReceiver.getPushListener() != null) {
                MiMessageReceiver.getPushListener().onResume(context);
            }
            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {
            if (JPushInterface.isPushStopped(context)) {
                JPushInterface.resumePush(context);
                if (JPushReceiver.getPushListener() != null) {
                    JPushReceiver.getPushListener().onResume(context);
                }
            }
        }
    }

}
