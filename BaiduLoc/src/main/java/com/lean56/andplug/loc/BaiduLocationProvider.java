package com.lean56.andplug.loc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Location Provider
 * <p/>
 * see {http://developer.baidu.com/map/index.php?title=android-locsdk/guide/v5-0}
 *
 * @author charles
 */
public class BaiduLocationProvider {

    private final static String TAG = BaiduLocationProvider.class.getSimpleName();

    private LocationClient locationClient;

    public LocationClient getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    public BaiduLocationProvider(Context context) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.setLocOption(BaiduLocationClientUtils.getBriefClientOption());
    }

    public BaiduLocationProvider(Context context, LocationClientOption option) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.setLocOption(option);
    }

    public void requestLocation(final LocationResultListener listener) {
        requestLocation(listener, 0);
    }

    public void requestLocation(final LocationResultListener listener, final int retry) {
        locationClient.start();
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                boolean success = true;
                if (TextUtils.isEmpty(city)) {
                    city = null;
                    success = false;
                    if (retry < 3) {
                        Log.e(TAG, "request location failure retry " + retry + " times");
                        requestLocation(listener, retry + 1);
                        return;
                    }
                } else {
                    // 大多数情况下，将"广州市"直接显示成"广州"
                    city = city.replaceFirst("市.*$", "");
                }

                if (isOneTimeLoc()) {
                    locationClient.unRegisterLocationListener(this);
                    locationClient.stop();
                }
                listener.onLocationResult(success, city, bdLocation.getAddrStr(), bdLocation.getLatitude(), bdLocation.getLongitude());
            }
        });
        // 0：正常发起了定位。
        // 1：服务没有启动。
        // 2：没有监听函数。
        // 6：请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。
        int code = locationClient.requestLocation();
        if (code == 6) {
            locationClient.requestOfflineLocation();
        }
    }

    /**
     * is one/multi time loc
     * ScanSpan
     * 当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。
     *
     * @return
     */
    private boolean isOneTimeLoc() {
        return getLocationClient().getLocOption().getScanSpan() < 1000;
    }

    public interface LocationResultListener {
        void onLocationResult(boolean success, String city, String area, double latitude, double longitude);
    }

}
