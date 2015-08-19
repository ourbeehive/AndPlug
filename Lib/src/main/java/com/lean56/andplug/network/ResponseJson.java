package com.lean56.andplug.network;

import java.io.Serializable;

/**
 * Response Json
 *
 * @author Charles
 */
public class ResponseJson implements Serializable {

    private boolean code;
    private int status;
    private String message;
    private String name;
    private String data;
    private ResponsePage pages;

    public ResponseJson() {
    }

    public ResponseJson(boolean code, int status, String message, String name, String data, ResponsePage pages) {
        this.code = code;
        this.status = status;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public ResponsePage getPages() {
        return pages;
    }

    public void setPages(ResponsePage pages) {
        this.pages = pages;
    }

    public boolean hasNextPage() {
        return this.pages != null && this.pages.hasNextPage();
    }
}
