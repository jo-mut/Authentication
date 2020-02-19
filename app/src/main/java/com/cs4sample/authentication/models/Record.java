package com.cs4sample.authentication.models;

public class Record {
    private String surname;
    private String otherNames;
    private String idNumber;
    private String dateOfBirth;
    private String gender;
    private String country;
    private String employerOrganisation;
    private String contributionPayer;
    private String insuranceType;
    private String profilePhoto;
    private String idFront;
    private String idBack;

    public Record() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmployerOrganisation() {
        return employerOrganisation;
    }

    public void setEmployerOrganisation(String employerOrganisation) {
        this.employerOrganisation = employerOrganisation;
    }

    public String getContributionPayer() {
        return contributionPayer;
    }

    public void setContributionPayer(String contributionPayer) {
        this.contributionPayer = contributionPayer;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdBack() {
        return idBack;
    }

    public void setIdBack(String idBack) {
        this.idBack = idBack;
    }
}
