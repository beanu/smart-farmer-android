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
import java.util.LinkedHashSet;
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
     * 设置别名
     * <p>
     * 华为
     * <p>
     * 不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     * ==========
     * 极光 别名
     * <p>
     * "" （空字符串）表示取消之前的设置。
     * 每次调用设置有效的别名，覆盖之前的设置。
     * 有效的别名组成：字母（区分大小写）、数字、下划线、汉字、特殊字符(v2.1.6支持)@!#$&*+=.|。
     * 限制：alias 命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
     * ==========
     * 小米 别名
     * <p>
     * 一个RegId可以被设置多个别名，如果设置的别名已经存在，会覆盖掉之前的别名。
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
     * 设置标签
     *
     * @param context
     * @param tag
     */
    public static void setTag(final Context context, String tag) {
//        if (TextUtils.isEmpty(tag))
//            return;
        if (RomUtil.rom() == PhoneTarget.EMUI) {
            Map<String, String> tagMap = new HashMap<>();
            tagMap.put("tag", tag);
            com.huawei.android.pushagent.api.PushManager.setTags(context, tagMap);
            return;

        }
        if (RomUtil.rom() == PhoneTarget.MIUI) {
            MiPushClient.subscribe(context, tag, null);
            return;
        }

        if (RomUtil.rom() == PhoneTarget.JPUSH) {

            Set<String> tagSet = new LinkedHashSet<>();
            if (!TextUtils.isEmpty(tag)) {
                String[] sArray = tag.split(",");
                for (String sTagItme : sArray) {
//                    if (!JPushUtils.isValidTagAndAlias(sTagItme)) {
//                        continue;
//                    }
                    tagSet.add(sTagItme);
                }
            }
            JPushInterface.setTags(context, tagSet, new TagAliasCallback() {
                @Override
                public void gotResult(int i, final String s, final Set<String> set) {
                    String logs;
                    switch (i) {
                        case 0:
                            logs = "Set tag and alias success";
                            break;
//                        case 6002:
//                            logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                            KLog.d(logs);
//                            if (JPushUtils.isConnected(context)) {
//                                Observable.empty().delay(1, TimeUnit.MINUTES).subscribe(new Action1<Object>() {
//                                    @Override
//                                    public void call(Object o) {
//
//                                        if (s != null) {
//                                            setAlias(context, s);
//                                        }
//                                        if (set != null) {
//                                            JPushInterface.setTags(context, set, this);
//                                        }
//
//                                    }
//                                });
//                            } else {
//                                KLog.d("No network");
//                            }
//                            break;
//                        default:
//                            logs = "Failed with errorCode = " + i;
//                            KLog.e("qcxy", logs);
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
