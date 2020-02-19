package com.cs4sample.authentication.models;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneralDataBaseClass {
    String id;
    String name;
    String code;

    public GeneralDataBaseClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
