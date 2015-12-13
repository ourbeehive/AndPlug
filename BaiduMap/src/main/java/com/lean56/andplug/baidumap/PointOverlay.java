package com.lean56.andplug.baidumap;

import android.os.Bundle;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.overlayutil.OverlayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Point Overlay
 * see {com.baidu.mapapi.overlayutil.PoiOverlay}
 *
 * @author Charles
 */
public class PointOverlay extends OverlayManager {

    private List<PointInfo> mPoints = new ArrayList<>();

    /**
     * Default Constructor
     *
     * @param baiduMap refer BaiduMap
     */
    public PointOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }


    /**
     * set point data
     *
     * @param points points data
     */
    public void setPoints(ArrayList<PointInfo> points) {
        if (null != points) {
            this.mPoints = points;
        }
    }

    /**
     * get the points on the overlay
     */
    public List<PointInfo> getPoints() {
        return this.mPoints;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        // check empty
        if (null == mPoints || mPoints.size() == 0) {
            return null;
        }

        // fill list
        List<OverlayOptions> markerList = new ArrayList<>();
        for (int i = 0; i < mPoints.size(); i++) {
            PointInfo pointInfo = mPoints.get(i);
            if (pointInfo.getLatLng() != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);

                markerList.add(new MarkerOptions().icon(genBitmapDescriptor(i, pointInfo)).extraInfo(bundle).position(pointInfo.getLatLng()));
            }
        }
        return markerList;
    }

    /**
     * override the method change the ui of marker
     *
     * @param index the index of point
     *
     * @param pointInfo the point info
     * @return the BitmapDescriptor
     */
    protected BitmapDescriptor genBitmapDescriptor(int index, PointInfo pointInfo) {
        return BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark.png");
    }

    /**
     * override the method to do click action
     *
     * @param index the index of point
     * @return the click result
     */
    public boolean onPointClick(int index) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker) || null == mBaiduMap) {
            return false;
        }
        if (marker.getExtraInfo() == null) {
            return false;
        }
        int index = marker.getExtraInfo().getInt("index");
        if (null == mPoints || index >= mPoints.size()) {
            return false;
        } else {
            return onPointClick(index);
        }
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}