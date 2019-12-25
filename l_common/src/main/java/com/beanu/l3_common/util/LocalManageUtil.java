package com.beanu.l3_common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.beanu.arad.Arad;

import java.util.Locale;

/**
 * 多语言管理
 *
 * @author Beanu
 */
public class LocalManageUtil {

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale() {
        return Constants.systemLocale;
    }


    /**
     * 获取选择的语言设置
     *
     * @return
     */
    public static Locale getSetLanguageLocale() {
        int language = Arad.preferences.getInteger(Constants.P_SETTING_LANGUAGE, -1);

        switch (language) {
            case 0:
                return Locale.CHINESE;
            case 1:
                return Locale.TAIWAN;
            case 2:
                return Locale.ENGLISH;
            case 3:
                return Locale.JAPANESE;
            case 4:
                return Locale.KOREAN;
            default:
                return getSystemLocale();
        }
    }

    public static void saveSelectLanguage(Context context, int select) {

        Arad.preferences.put(Constants.P_SETTING_LANGUAGE, select);
        Arad.preferences.flush();

        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale());
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);

        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public static void saveSystemCurrentLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        Constants.systemLocale = locale;
    }

    public static void onConfigurationChanged(Context context) {
        saveSystemCurrentLanguage();
        setLocal(context);
        setApplicationLanguage(context);
    }
}
