package com.lean56.andplug.baidumap;

import com.baidu.mapapi.model.LatLng;

/**
 * LatLngUtils
 *
 * @author Charles
 */
public class LatLngUtils {

    /**
     * gen LatLng with string value lat and lng
     * @param lat
     * @param lng
     * @return
     */
    public static LatLng genLatlng(String lat, String lng) {
        try {
            return new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * gen LatLng with double value lat and lng
     * @param lat
     * @param lng
     * @return
     */
    public static LatLng genLatlng(Double lat, Double lng) {
        if (null == lat || 0d == lat || null == lng || 0d == lng)
            return null;
        else
            return new LatLng(lat, lng);
    }

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
        return latLng.longitude > 73 && latLng.longitude < 135 && latLng.latitude > 3 && latLng.latitude < 54;
    }
}
