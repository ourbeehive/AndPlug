package com.lean56.andplug.network;

import java.io.Serializable;

/**
 * Response Page
 *
 * @author Charles
 */
public class ResponsePage implements Serializable {

    private long totalCount;
    private int pageSize;
    private int pageNo;

    public ResponsePage() {
    }

    public ResponsePage(long totalCount, int pageSize, int pageNo) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean hasNextPage() {
        return totalCount > pageSize * pageNo;
    }
}
