package com.lean56.andplug.getuipush;

/**
 * Entity named PayloadPushMsg.
 *
 * THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
 * Enable "keep" sections if you want to edit.
*/
public class PayloadPushMsg implements java.io.Serializable {

    private String title;
    private String message;
    private String action;

    public PayloadPushMsg() {
    }

    public PayloadPushMsg(String title, String message, String action) {
        this.title = title;
        this.message = message;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
