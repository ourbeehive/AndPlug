package com.lean56.andplug.map;

/**
 * LatLngUtils
 *
 * @author Charles
 */
public class LatLngUtils {

    public static boolean isChinaLoc(Double lat, Double lng) {
        if (null == lat || null == lng)
            return false;

        return isChinaLoc(new SimpleLatLng(lat, lng));
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
    public static boolean isChinaLoc(SimpleLatLng latLng) {
        return latLng.getLongitude() > 73 && latLng.getLongitude() < 135 && latLng.getLatitude() > 3 && latLng.getLatitude() < 54;
    }
}
