package com.cs4sample.authentication.models;

public class Gender {
    int id;
    String genderName;
    String genderCharacter;

    public Gender() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderCharacter() {
        return genderCharacter;
    }

    public void setGenderCharacter(String genderCharacter) {
        this.genderCharacter = genderCharacter;
    }
}
