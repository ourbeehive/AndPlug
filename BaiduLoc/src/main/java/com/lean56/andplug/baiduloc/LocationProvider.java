package com.lean56.andplug.baiduloc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Location Provider
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class LocationProvider {
    private final static String TAG = LocationProvider.class.getSimpleName();

    private LocationClient locationClient;
    private boolean intervalLoc;

    public LocationClient getLocationClient() {
        return locationClient;
    }

    public void setLocationClient(LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    public LocationProvider(Context context, boolean intervalLoc) {
        locationClient = new LocationClient(context.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);//返回的定位结果需要包含地址信息
        //option.setAddrType("all");
        if (intervalLoc) {
            option.setScanSpan(10000);//10秒周期定位
        }
        this.intervalLoc = intervalLoc;
        option.setLocationNotify(false);
        option.setNeedDeviceDirect(true);//返回的定位结果需要包含手机机头的方向
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

                if (!intervalLoc) {
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

    public interface LocationResultListener {
        void onLocationResult(boolean success, String city, String area, double latitude, double longitude);
    }

}
