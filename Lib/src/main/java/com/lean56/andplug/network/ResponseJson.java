package com.lean56.andplug.network;

import java.io.Serializable;

/**
 * Response Json
 * all the
 *
 * @author Charles
 */
public class ResponseJson implements Serializable {

    private int status;
    private String msg;
    private String data;
    private boolean page;
    private ResponsePage pageInfo;

    public ResponseJson() {
    }

    public ResponseJson(int status, String msg, String data, boolean page, ResponsePage pageInfo) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.page = page;
        this.pageInfo = pageInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public ResponsePage getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(ResponsePage pageInfo) {
        this.pageInfo = pageInfo;
    }

    // [+] page
    public boolean hasNextPage() {
        return this.page &&this.pageInfo != null && this.pageInfo.hasNextPage();
    }
    // [-] page

    // [+] status
    public boolean statusInfo() {
        return status < 200;
    }

    public boolean statusSuccess() {
        return status >= 200 && status < 300;
    }

    public boolean statusRedirect() {
        return status >= 300 && status < 400;
    }

    public boolean statusAuthFailure() {
        return status == 404;
    }

    public boolean statusClientError() {
        return status >= 400 && status < 500;
    }

    public boolean statusServerError() {
        return status >= 500;
    }
    // [-] status
}
