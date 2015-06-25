package com.lean56.andplug.baidumap;

import com.baidu.mapapi.search.core.SearchResult;

import java.util.List;

/**
 * PointResult
 *
 * see {com.baidu.mapapi.search.poi.PoiResult}
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class PointResult extends SearchResult {

    private List<PointInfo> points;

    public PointResult() {}

    public PointResult(List<PointInfo> points) {
        this.points = points;
    }

    PointResult(ERRORNO var1) {
        super(var1);
    }

    public List<PointInfo> getPoints() {
        return points;
    }

    public void setPoints(List<PointInfo> points) {
        this.points = points;
    }
}
