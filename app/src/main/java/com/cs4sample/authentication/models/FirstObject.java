package com.cs4sample.authentication.models;

public class FirstObject {
    String id;
    boolean isOkay;
    String message;

    public FirstObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOkay() {
        return isOkay;
    }

    public void setOkay(boolean okay) {
        isOkay = okay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
