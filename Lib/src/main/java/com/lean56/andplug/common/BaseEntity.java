package com.lean56.andplug.common;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author Charles
 */
public class BaseEntity implements Serializable {

    public BaseEntity() {
    }

    public BaseEntity(int viewType) {
        this.viewType = viewType;
    }

    private int viewType = 0;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
