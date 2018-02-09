package com.beanu.l3_common.support;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.beanu.arad.Arad;

import java.util.Stack;

/**
 * @author lizhi
 * @date 2017/11/3.
 */

public class ActivityHelper implements Application.ActivityLifecycleCallbacks{

    private static boolean isAttached = false;
    private static final ActivityHelper SINGLETON = new ActivityHelper();

    private final Stack<Activity> activityStack = new Stack<>();

    public static void attachedToApplication(Application application){
        if (isAttached){
            return;
        }
        isAttached = true;
        application.registerActivityLifecycleCallbacks(SINGLETON);
    }

    public static ActivityHelper getInstance(){
        return SINGLETON;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityStack.push(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityStack.remove(activity);
    }

    @Nullable
    public Activity getTopActivity(){
        return getTopActivity(Activity.class);
    }

    public <T extends Activity> T getTopActivity(Class<T> tClass){
        if (activityStack.isEmpty()) {
            return null;
        }
        for (int i = activityStack.size() - 1; i >= 0; --i){
            Activity activity = activityStack.get(i);
            if (!activity.isFinishing()){
                if (tClass.isAssignableFrom(activity.getClass())){
                    return tClass.cast(activity);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public Stack<Activity> getActivityStack(){
        return activityStack;
    }

    public static Context getContext(){
        Context context = getInstance().getTopActivity();
        if (context == null){
            context = Arad.app;
        }
        return context;
    }
}
