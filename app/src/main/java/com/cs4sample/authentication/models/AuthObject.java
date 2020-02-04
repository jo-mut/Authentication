package com.cs4sample.authentication.models;

public class AuthObject {
    private String id;
    private FirstAuthObject firstAuthObject;
    private User user;

    public AuthObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FirstAuthObject getFirstAuthObject() {
        return firstAuthObject;
    }

    public void setFirstAuthObject(FirstAuthObject firstAuthObject) {
        this.firstAuthObject = firstAuthObject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
