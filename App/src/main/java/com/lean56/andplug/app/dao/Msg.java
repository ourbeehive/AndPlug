package com.lean56.andplug.app.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table MSG.
 */
public class Msg implements java.io.Serializable {

    private Long id;
    private String title;
    private String user;
    private String content;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Msg() {
    }

    public Msg(Long id) {
        this.id = id;
    }

    public Msg(Long id, String title, String user, String content) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}