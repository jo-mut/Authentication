package com.cs4sample.authentication.models;

public class Insurance {
    String id;
    String type;
    String code;
    String contributorId;

    public Insurance() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
    }
}
