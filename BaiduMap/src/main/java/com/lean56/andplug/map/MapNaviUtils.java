package com.lean56.andplug.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * MapNaviUtils
 * AMAP URI API: http://api.amap.com/uri/uriandroid
 * BaiduMap URI API: http://developer.baidu.com/map/uri-introandroid.htm
 *
 * @author Charles
 */
public class MapNaviUtils {

    public final static String BaiduMapPackageName = "com.baidu.BaiduMap";
    public final static String AMAPPackageName = "com.autonavi.minimap";

    // baidumap call source, rule: companyName|appName
    private final static String PARAM_SRC = "lean56|map";
    // 起终点是否偏移(0:lat 和 lon是已经加密后的,不需要国测加密; 1:需要国测加密)
    private final static int PARAM_DEV = 0;

    /**
     * go to map marker
     */
    public static void naviToMarker(Context context, double longitude, double latitude, String title, String content) {
        String location = String.format("%s,%s", latitude, longitude);

        String baiduMapMarkerUri = String.format("bdapp://map/marker?location=%s&title=%s&content=%s&src=%s", location, title, content, PARAM_SRC);
        String aMapMarkerUri = String.format("androidamap://viewMap?sourceApplication=baton&poiname=%s&lat=%s&lon=%s&dev=%s",title, latitude, longitude, PARAM_DEV);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // get the default map engine
        if (ApkInstaller.checkAppInstalled(context, BaiduMapPackageName)) {
            intent.setData(Uri.parse(baiduMapMarkerUri));
            intent.setFlags(Intent.URI_INTENT_SCHEME);
            intent.setPackage(BaiduMapPackageName);
        } else if (ApkInstaller.checkAppInstalled(context, AMAPPackageName)) {
            intent.setData(Uri.parse(aMapMarkerUri));
            intent.setFlags(Intent.URI_INTENT_SCHEME);
            intent.setPackage(AMAPPackageName);
        } else {
            // web
            String url = String.format("http://api.map.baidu.com/marker?location=%s&title=%s&content=%s&output=html&src=%s", location, title, content, PARAM_SRC);
            intent.setData(Uri.parse(url));
        }

        context.startActivity(intent);
    }
}
