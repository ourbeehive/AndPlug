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
     * if the network connected
     */
    public static boolean isNetworkConnected(Context context, int network) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(network);
        return ((info != null) && (info.isConnected()));
    }

    public static boolean isWiFiConnected(Context context) {
        return isNetworkConnected(context, ConnectivityManager.TYPE_WIFI);
    }

}
