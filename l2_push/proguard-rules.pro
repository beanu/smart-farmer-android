# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Beanu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#极光混淆
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#小米混淆(自定义的广播接收器，不能混淆)
-keep class com.beanu.l2_push.receiver.MiMessageReceiver {*;}
-dontwarn com.xiaomi.push.**

#华为混淆
-keep class com.huawei.android.pushagent.**{*;}
-keep class com.huawei.android. pushselfshow.**{*;}
-keep class com.huawei.android. microkernel.**{*;}
-keep class com.baidu.mapapi.**{*;}