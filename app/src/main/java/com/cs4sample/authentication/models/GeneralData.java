package com.cs4sample.authentication.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GeneralData {
    String $id;
    String resultList;

    public GeneralData() {
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getResultList() {
        return resultList;
    }

    public void setResultList(String resultList) {
        this.resultList = resultList;
    }

    public GeneralData fromJson(JSONObject object) {
        GeneralData generalData = new GeneralData();
        try {
            generalData.$id = object.getString("$id");
            generalData.resultList = object.getString("Result");
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return generalData;
    }
}
