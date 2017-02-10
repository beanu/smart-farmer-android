package com.beanu.l2_push;

import android.content.Context;

/**
 * 在华为推送中，不会回调onMessage(),onMessageClicked()和onAlias()方法
 * 如上两个方法不会回调是因为设计的时候，通知栏的点击事件由后台负责控制,手机端值负责处理透传消息
 * <p>
 * Created by Beanu on 2017/2/10.
 */

public interface IPushListener {
    /**
     * 注册成功之后回调
     *
     * @param context
     * @param registerID
     */
    void onRegister(Context context, String registerID);

    /**
     * 取消注册成功
     *
     * @param context
     */
    void onUnRegister(Context context);

    /**
     * 暂停推送
     *
     * @param context
     */
    void onPaused(Context context);

    /**
     * 开启推送
     *
     * @param context
     */
    void onResume(Context context);

    /**
     * 通知下来之后
     *
     * @param context
     * @param message
     */
    void onMessage(Context context, PushMessage message);

    /**
     * 通知栏被点击之后
     *
     * @param context
     * @param message
     */
    void onMessageClicked(Context context, PushMessage message);

    /**
     * 透传消息
     *
     * @param context
     * @param message
     */
    void onCustomMessage(Context context, PushMessage message);


    /**
     * 别名设置成功的回调
     *
     * @param context
     * @param alias
     */
    void onAlias(Context context, String alias);
}
