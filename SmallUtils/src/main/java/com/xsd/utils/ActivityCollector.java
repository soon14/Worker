package com.xsd.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * add and remove Activity
 * use this into BaseActivity
 */

public class ActivityCollector {

    private static ActivityCollector sInstance = new ActivityCollector();

    private WeakReference<Activity> sCurrentActivityWeakRef;

    private Object activityUpdateLock = new Object();
    private ActivityCollector() {

    }

    public static ActivityCollector getInstance() {
        return sInstance;
    }
    //获取当前栈顶的Activity
    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        synchronized (activityUpdateLock){
            if (sCurrentActivityWeakRef != null) {
                currentActivity = sCurrentActivityWeakRef.get();
            }
        }
        return currentActivity;
    }
    //设置栈顶的Activity
    public void setCurrentActivity(Activity activity) {
        synchronized (activityUpdateLock){
            sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
        }

    }

    public static List<Activity> activities = new ArrayList<Activity>();

    public static boolean hasActivity(String targetActivityName) {
        if (activities==null||activities.size()==0)return false;
        for (Activity activity : activities) {
            L.e(activity.getClass().getSimpleName()+":"+targetActivityName);
            if (activity.getClass().getSimpleName().equals(targetActivityName))
                return true;
        }
        return false;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static <T extends Activity>void finishActivity(Class<T> activity) {
        if (activities!=null&&activities.size()>0)for (Activity a:activities){
            if ((a.getClass().getName()).equals(activity.getName()))a.finish();
        }
    }
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) activity.finish();
        }
    }


}
