package com.lean56.andplug.map;

/**
 * Point Overlay
 * see {com.baidu.mapapi.overlayutil.PoiOverlay}
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class PointOverlay /*extends OverlayManager*/ {

    /*private PointResult pointResult = null;

    public final static String BUNDLE_KEY_POINT_INFO = "BUNDLE_KEY_POINT_INFO";

    public PointOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    public PointResult getPointResult() {
        return pointResult;
    }

    public void setPointResult(PointResult pointResult) {
        this.pointResult = pointResult;
    }

    @Override
    public List<OverlayOptions> getOverlayOptions() {
        if (null == pointResult || null == pointResult.getPoints() || pointResult.getPoints().size() == 0) {
            return null;
        }

        List<OverlayOptions> ops = new ArrayList<>();
        List<PointInfo> pois = pointResult.getPoints();

        for (PointInfo pointInfo : pois) {
            if (pointInfo.getLocation() != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(BUNDLE_KEY_POINT_INFO, pointInfo);

                BitmapDescriptor bitmapDescriptor;
                // support 1 - 19
                if (pointInfo.getId() > 0 && pointInfo.getId() < 20) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + String.valueOf(pointInfo.getId()) + ".png");
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark.png");
                }
                ops.add(new MarkerOptions().icon(bitmapDescriptor).extraInfo(bundle).position(pointInfo.getLocation().toBaiduLatLng()));
            }
        }
        return ops;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }*/
}