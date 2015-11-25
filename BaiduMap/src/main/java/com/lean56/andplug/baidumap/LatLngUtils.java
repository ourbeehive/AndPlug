package com.lean56.andplug.baidumap;

import com.baidu.mapapi.model.LatLng;

/**
 * LatLngUtils
 *
 * @author Charles
 */
public class LatLngUtils {

    public static boolean isChinaLoc(double lat, double lng) {
        return isChinaLoc(new LatLng(lat, lng));
    }

    /**
     * is the latlng loc in china
     * <p/>
     * 经度范围：73°33′E至135°05′E
     * 纬度范围：3°51′N至53°33′N
     *
     * @param latLng
     * @return
     */
    public static boolean isChinaLoc(LatLng latLng) {
        return latLng.longitude > 73 && latLng.longitude< 135 && latLng.latitude > 3 && latLng.latitude < 54;
    }
}
