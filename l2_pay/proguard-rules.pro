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

#weixin
-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

#ipay
-dontwarn  com.beanu.l2_pay.**
-keep public class com.beanu.l2_pay.** {
    !private <fields>;
    !private <methods>;
}

-keep public interface com.beanu.l2_pay.**{*;}
-keep public enum com.beanu.l2_pay.**{*;}