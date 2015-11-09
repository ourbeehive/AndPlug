package com.lean56.andplug.network;

import java.io.Serializable;

/**
 * Response Json
 * all the
 *
 * @author Charles
 */
public class ResponseJson implements Serializable {

    private boolean code;
    private int status;
    private String msg;
    private String name;
    private String data;
    private ResponsePage pages;

    public ResponseJson() {
    }

    public ResponseJson(boolean code, int status, String msg, String name, String data, ResponsePage pages) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.name = name;
        this.data = data;
        this.pages = pages;
    }

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // [+] page
    public ResponsePage getPages() {
        return pages;
    }

    public void setPages(ResponsePage pages) {
        this.pages = pages;
    }

    public boolean hasNextPage() {
        return this.pages != null && this.pages.hasNextPage();
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

    public boolean statusClientError() {
        return status >= 400 && status < 500;
    }

    public boolean statusServerError() {
        return status >= 500;
    }
    // [-] status
}
