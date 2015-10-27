package com.lean56.andplug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Base Application
 * Toast shower
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public class BaseApplication extends Application {

    protected static String PREF_NAME = "lean56.pref";
    private static String REFRESH_TIME = "refresh_time.pref";

    static Context _context;
    static Resources _resource;

    public static float sDensity;
    public static int sWidthDp;
    public static int sWidthPix;
    public static int sHeightPix;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();

        calcDisplayMetrics();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // add multidex support
        // MultiDex.install(this);
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    private void calcDisplayMetrics() {
        sDensity = getResources().getDisplayMetrics().density;
        sWidthPix = getResources().getDisplayMetrics().widthPixels;
        sHeightPix = getResources().getDisplayMetrics().heightPixels;
        sWidthDp = (int) (sWidthPix / sDensity);
    }

    // [+] Shared Preference
    public static SharedPreferences getPreferences() {
        return getPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
    }

    public static SharedPreferences getPreferences(String name) {
        return getPreferences(name, Context.MODE_MULTI_PROCESS);
    }

    public static SharedPreferences getPreferences(String name, int mode) {
        return context().getSharedPreferences(name, mode);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void set(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void set(String key, Serializable entity) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, JSON.toJSONString(entity));
        editor.apply();
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public static <T> T get(String key, Class<T> clazz, T t) {
        String strValue = getPreferences().getString(key, "");
        if (TextUtils.isEmpty(strValue)) {
            return t;
        } else {
            return JSON.parseObject(strValue, clazz);
        }
    }
    // [-] Shared Preference

    // [+] Last Refresh Time
    public static void setRefreshTime(String key, long value) {
        SharedPreferences preferences = getPreferences(REFRESH_TIME);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getRefreshTime(String key) {
        return getPreferences(REFRESH_TIME).getLong(key, 0L);
    }
    // [-] Last Refresh Time

    // [+] Display Screen Param
    public static int[] getDisplaySize() {
        SharedPreferences pref = getPreferences();
        return new int[]{
                pref.getInt("screen_width", 480),
                pref.getInt("screen_height", 854)};
    }

    public static void saveDisplaySize(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt("screen_width", displaymetrics.widthPixels);
        editor.putInt("screen_height", displaymetrics.heightPixels);
        editor.putFloat("density", displaymetrics.density);
        editor.apply();
    }
    // [-] Display Screen Param

    // [+] Show Toast
    private static String lastToast = "";
    private static long lastToastTime;

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon, int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon, int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        // return if message is empty
        if (TextUtils.isEmpty(message))
            return;

        // return if message same as the last in a short time(2s)
        if (message.equalsIgnoreCase(lastToast) && Math.abs(System.currentTimeMillis() - lastToastTime) < 2000)
            return;

        View view = LayoutInflater.from(context()).inflate(R.layout.toast, null);
        ((TextView) view.findViewById(R.id.toast_message)).setText(message);
        if (0 != icon) {
            ((ImageView) view.findViewById(R.id.toast_icon)).setImageResource(icon);
            view.findViewById(R.id.toast_icon).setVisibility(View.VISIBLE);
        }
        Toast toast = new Toast(context());
        toast.setView(view);
        if (gravity == Gravity.CENTER) {
            toast.setGravity(gravity, 0, 0);
        } else {
            toast.setGravity(gravity, 0, 35);
        }

        toast.setDuration(duration);
        toast.show();
        lastToast = message;
        lastToastTime = System.currentTimeMillis();
    }
    // [-] Show Toast

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // auto gc when the memory is low
        System.gc();
    }

}
