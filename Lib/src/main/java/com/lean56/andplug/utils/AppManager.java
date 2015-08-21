package com.lean56.andplug.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Application Activity Manager
 * Activity manage and exit the application
 *
 * @author liux (http://my.oschina.net/liux)
 * @author Charles
 * @version 1.1
 * @since 2012-3-21
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * support a method to get a instance for the outside
     */
    public static AppManager getAppManager() {
        if (null == instance) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * add the Activity into stack
     */
    synchronized public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get the current Activity(the last push to stack)
     */
    synchronized public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * end the current Activity(the last push to stack)
     */
    synchronized public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * end the Activity with class instance
     * remove the activity from stack and finish it
     */
    synchronized public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * end the Activity with class name
     */
    synchronized public void finishActivity(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(activityStack);

        for (Activity activity : tempActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * end the Activity with class name
     */
    synchronized public void finishActivity(Class<?> cls, int resultCode) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(activityStack);

        for (Activity activity : tempActivityStack) {
            if (activity.getClass().equals(cls)) {
                activity.setResult(resultCode);
                finishActivity(activity);
            }
        }
    }

    /**
     * end all the activities except Activity with class name
     */
    synchronized public void finishAllActivityExcept(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(activityStack);

        for (Activity activity : tempActivityStack) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * end all the activity
     */
    synchronized public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (null !=activity) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * exit the application
     */
    public void exitApp() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
