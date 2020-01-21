package com.cs4sample.authentication.models;

public class UserAuth {
    private boolean isRenewalPasswordRequest = false;
    private User user;

    public UserAuth() {
    }

    public boolean isRenewalPasswordRequest() {
        return isRenewalPasswordRequest;
    }

    public void setRenewalPasswordRequest(boolean renewalPasswordRequest) {
        isRenewalPasswordRequest = renewalPasswordRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
