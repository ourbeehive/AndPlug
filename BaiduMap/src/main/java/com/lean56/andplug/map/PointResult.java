package com.lean56.andplug.map;

import java.util.List;

/**
 * PointResult
 *
 * see {com.baidu.mapapi.search.poi.PoiResult}
 *
 * @author Charles
 */
public class PointResult {

    private List<PointInfo> points;

    public PointResult() {}

    public PointResult(List<PointInfo> points) {
        this.points = points;
    }

    public List<PointInfo> getPoints() {
        return points;
    }

    public void setPoints(List<PointInfo> points) {
        this.points = points;
    }
}
