package com.cs4sample.authentication.models;

import org.json.JSONObject;

import java.util.List;

public class Main{
    int id;
    boolean isOkay;
    String $id;
    String message;
    List<GeneralData> results;

    public Main() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
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

    public List<GeneralData> getResults() {
        return results;
    }

    public void setResults(List<GeneralData> results) {
        this.results = results;
    }
}
