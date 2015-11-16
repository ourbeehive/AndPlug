package com.lean56.andplug.utils;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Phone Type
 *
 * @author Charles
 */
public class PhoneType {

    /**
     * is the CPU of Mobile device is x86
     */
    public static boolean isX86() {
        String arch = System.getProperty("os.arch").toLowerCase();
        return arch.equals("i686") || arch.equals("x86");
    }

    /**
     * is Flyme OS
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * is Xiaomi mobile device
     */
    public static boolean isXiaomi() {
        return Build.MANUFACTURER.equalsIgnoreCase("Xiaomi");
    }

    /**
     * TODO
     * is MIUI OS
     */
    public static boolean isMIUI() {
        return false;
    }
}
