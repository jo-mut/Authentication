package com.cs4sample.authentication.models;

public class MainObject {
    private String id;
    private FirstObject firstObject;
    private User user;

    public MainObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FirstObject getFirstObject() {
        return firstObject;
    }

    public void setFirstObject(FirstObject firstObject) {
        this.firstObject = firstObject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
