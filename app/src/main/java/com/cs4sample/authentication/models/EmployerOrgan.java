package com.cs4sample.authentication.models;

public class EmployerOrgan extends GeneralDataBaseClass {
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
}
