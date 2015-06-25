package com.lean56.andplug.baidumap;

import java.io.Serializable;

/**
 * PointInfo
 * see {com.baidu.mapapi.search.core.PoiInfo}
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class PointInfo implements Serializable {

    private long id;
    private String name;
    private String address;
    private String phone;
    private POINTTYPE type;
    private LatLng location;

    public PointInfo() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public POINTTYPE getType() {
        return type;
    }

    public void setType(POINTTYPE type) {
        this.type = type;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * point type
     */
    public static enum POINTTYPE {

        /*STORE(1, R.drawable.ic_marker_store), WAREHOUSE(2, R.drawable.ic_marker_warehouse), VEHICLE(3, R.drawable.ic_marker_vehicle);

        private int key;
        private int resId;

        private POINTTYPE(int key, int resId) {
            this.key = key;
            this.resId = resId;
        }

        public static POINTTYPE getTypeByKey(int key) {
            for (POINTTYPE type : POINTTYPE.values()) {
                if (type.getKey() == key) {
                    return type;
                }
            }
            return null;
        }

        private POINTTYPE(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }*/
    }
}
