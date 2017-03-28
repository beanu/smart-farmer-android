//package com.beanu.sf.ui.layer2.push;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.beanu.l2_push.IPushListener;
//import com.beanu.l2_push.PushMessage;
//
///**
// * UI线程
// * Created by Beanu on 2017/2/10.
// */
//
//public class PushListener implements IPushListener {
//
//    private static final String TAG = "接收到了";
//
//    @Override
//    public void onRegister(Context context, String registerID) {
//        Log.d(TAG, registerID);
//    }
//
//    @Override
//    public void onUnRegister(Context context) {
//        Log.d(TAG, "onUnRegister");
//
//    }
//
//    @Override
//    public void onPaused(Context context) {
//        Log.d(TAG, "onPaused");
//
//    }
//
//    @Override
//    public void onResume(Context context) {
//        Log.d(TAG, "onResume");
//
//    }
//
//    @Override
//    public void onMessage(Context context, PushMessage message) {
//        Log.d(TAG, "通知：" + message.toString());
//
//    }
//
//    @Override
//    public void onMessageClicked(Context context, PushMessage message) {
//        Log.d(TAG, "通知被点击：" + message.toString());
//
//    }
//
//    @Override
//    public void onCustomMessage(Context context, PushMessage message) {
//        Log.d(TAG, "自定义消息：" + message.toString());
//
//    }
//
//    @Override
//    public void onAlias(Context context, String alias) {
//        Log.d(TAG, "别名：" + alias);
//
//    }
//}
