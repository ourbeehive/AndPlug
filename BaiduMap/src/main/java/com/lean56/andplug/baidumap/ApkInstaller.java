package com.lean56.andplug.baidumap;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.List;

/**
 * ApkInstallChecker
 *
 * @author Charles
 */
public class ApkInstaller {

    /**
     * 根据packageName判断是否安装有该app
     *
     * @param context
     * @param packageName
     */
    public static boolean checkAppInstalled(Context context, String packageName) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equalsIgnoreCase(packageName)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

}
