package com.lean56.andplug.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network utilities
 *
 * @author Charles
 */
public class NetworkUtils {

    /**
     * if network connected
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm)
            return false;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * get connected network type
     * @param context
     * @return
     */
    public static int getConnectedNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm)
            return -1;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected() ? activeNetwork.getType() : -1;
    }

    /**
     * is WiFi Connected
     * @param context
     * @return
     */
    public static boolean isWiFiConnected(Context context) {
        return ConnectivityManager.TYPE_WIFI == getConnectedNetworkType(context);
    }

}
