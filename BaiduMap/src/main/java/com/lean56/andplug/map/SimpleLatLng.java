package com.lean56.andplug.map;

import java.io.Serializable;

/**
 * SimpleLatLng
 * see {com.baidu.mapapi.model.SimpleLatLng}
 * to fix BaiduLatLng can not Serializable issue
 *
 * @author Charles
 */
public class SimpleLatLng implements Serializable {

    private double latitude;
    private double longitude;

    public SimpleLatLng() {}

    public SimpleLatLng(double lat, double longi) {
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

    /**
     * transfer to baidu latlng
     * @return
     */
    public com.baidu.mapapi.model.LatLng toBaiduLatLng() {
        return new com.baidu.mapapi.model.LatLng(latitude, longitude);
    }
}
