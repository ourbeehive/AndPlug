package com.lean56.andplug.map;

import com.baidu.mapapi.model.LatLng;

/**
 * PointInfo
 * see {com.baidu.mapapi.search.core.PoiInfo}
 *
 * @author Charles
 */
public class PointInfo {

    private long id;
    private int type;
    private String name;
    private String address;
    private String phone;
    private LatLng latLng;
    private Object extInfo;

    public PointInfo() {}

    public PointInfo(LatLng latLng) {
        this.latLng = latLng;
    }

    public PointInfo(long id, int type, String name, String address, String phone, LatLng latLng, Object extInfo) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latLng = latLng;
        this.extInfo = extInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Object getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Object extInfo) {
        this.extInfo = extInfo;
    }
}
