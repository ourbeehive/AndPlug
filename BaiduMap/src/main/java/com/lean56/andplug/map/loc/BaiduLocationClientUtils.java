package com.lean56.andplug.map.loc;

import com.baidu.location.LocationClientOption;

/**
 * BaiduLocationClientUtils
 * see {http://developer.baidu.com/map/index.php?title=android-locsdk/guide/v5-0}
 *
 * @author Charles
 */
public class BaiduLocationClientUtils {

    public static LocationClientOption getBriefClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        // option.setScanSpan(1);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(false);
        option.setLocationNotify(false);
        return option;
    }

    public static LocationClientOption getFullClientOption(int scanSpan) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(scanSpan);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        option.setLocationNotify(false);
        return option;
    }

    public static LocationClientOption getFullClientOption() {
        return getFullClientOption(10000);
    }
}
