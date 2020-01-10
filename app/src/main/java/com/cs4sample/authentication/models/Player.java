package com.cs4sample.authentication.models;

public class Player {
    public static final String PLAYER_TB = "players_tb";
    public static final String ROW_ID = "id";
    public static final String ROW_NAME = "name";
    public static final String ROW_AGE = "age";
    public static final String ROW_POSITION = "position";
    public static final String ROW_IMAGE = "photo";

    int id;
    String name;
    String age;
    String position;
    byte[] image;

    public Player() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
