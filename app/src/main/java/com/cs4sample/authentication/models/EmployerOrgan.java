package com.cs4sample.authentication.models;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployerOrgan extends GeneralDataBaseClass {
    String id;
    String $id;
    String name;
    String code;
    String insuranceId;
    String commune;

    public EmployerOrgan() {
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public EmployerOrgan fromJson(JSONObject object) {
        EmployerOrgan employerOrgan = new EmployerOrgan();
        try {
            employerOrgan.$id = object.getString("$id");
            employerOrgan.commune = object.getString("commune");
            employerOrgan.code = object.getString("code");
            employerOrgan.id = object.getString("id");
            employerOrgan.insuranceId = object.getString("insuaranceId");
            employerOrgan.name = object.getString("name");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return employerOrgan;
    }
}
