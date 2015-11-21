package com.lean56.andplug.map;

import android.os.Bundle;
import android.widget.Button;
import com.baidu.mapapi.BMapManager;
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

    private PointResult mPointResult = null;

    /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */
    public PointOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    /**
     * 设置POI数据
     *
     * @param pointResult 设置POI数据
     */
    public void setData(PointResult pointResult) {
        this.mPointResult = pointResult;
    }

    /**
     * 获取该 PointOverlay 的 poi数据
     */
    public PointResult getPointResult() {
        return mPointResult;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        // check empty
        if (null == mPointResult) {
            return null;
        }
        List<PointInfo> pois = mPointResult.getPoints();
        if (null == pois || pois.size() == 0) {
            return null;
        }

        // fill list
        List<OverlayOptions> markerList = new ArrayList<>();

        for (int i = 0; i < pois.size(); i++) {
            PointInfo pointInfo = pois.get(i);
            if (pointInfo.getLocation() != null) {
                markerList.add(genMarkerOptions(i, pointInfo));
            }
        }
        return markerList;
    }

    protected MarkerOptions genMarkerOptions(int index, PointInfo pointInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);

        BitmapDescriptor bitmapDescriptor;
        // support 1 - 19
        if (pointInfo.getId() > 0 && pointInfo.getId() < 20) {
            bitmapDescriptor = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + String.valueOf(pointInfo.getId()) + ".png");
        } else {
            bitmapDescriptor = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark.png");
        }
        return new MarkerOptions().icon(bitmapDescriptor).extraInfo(bundle).position(pointInfo.getLocation().toBaiduLatLng());
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param index 被点击的poi在
     *              {@link PointResult#getPoints()} 中的索引
     * @return
     */
    public boolean onPointClick(int index) {
        if (null == mPointResult || null == mPointResult.getPoints() || null == mPointResult.getPoints().get(index)) {
            return false;
        }
        if (null == mBaiduMap) {
            return false;
        }
        PointInfo pointInfo = mPointResult.getPoints().get(index);

        //创建InfoWindow展示的view
        Button button = new Button(BMapManager.getContext());
        button.setBackgroundResource(android.R.drawable.alert_dark_frame);
        //定义用于显示该InfoWindow的坐标点
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(button, pointInfo.getLocation().toBaiduLatLng(), -47);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPointClick(marker.getExtraInfo().getInt("index"));
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}