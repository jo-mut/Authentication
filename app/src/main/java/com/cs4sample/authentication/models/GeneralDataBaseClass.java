package com.cs4sample.authentication.models;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneralDataBaseClass {
    String $id;
    String id;
    String name;
    String code;

    public GeneralDataBaseClass() {
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
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

    public GeneralDataBaseClass fromJson(JSONObject object) {
        GeneralDataBaseClass baseClass = new GeneralDataBaseClass();
        try {
            baseClass.$id = object.getString("$id");
            baseClass.id = object.getString("id");
            baseClass.code = object.getString("code");
            baseClass.name = object.getString("name");
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return baseClass;
    }
}
