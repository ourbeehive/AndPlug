package com.lean56.andplug.baidumap;

import java.io.Serializable;

/**
 * LatLng
 * see {com.baidu.mapapi.model.LatLng}
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class LatLng implements Serializable {

    private double latitude;
    private double longitude;

    public LatLng() {}

    public LatLng(double lat, double longi) {
        this.latitude = lat;
        this.longitude = longi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public com.baidu.mapapi.model.LatLng toBaiduLatLng() {
        return new com.baidu.mapapi.model.LatLng(latitude, longitude);
    }
}
