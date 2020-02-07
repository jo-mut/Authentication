package com.cs4sample.authentication.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main{
    boolean isOkay;
    String $id;
    String message;
    String results;

    public Main() {
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

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public static Main fromJson(JSONObject jsonObject) {
        Main main = new Main();
        try {
            if (jsonObject != null) {
                main.$id = jsonObject.getString("$id");
                main.isOkay = jsonObject.getBoolean("IsOkay");
                main.message = jsonObject.getString("Message");
                main.results = jsonObject.getString("Result");
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return main;
    }

    public static List<Main> fromJsonArray(JSONArray jsonArray) {
        List<Main> mainList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i <jsonArray.length(); i++) {
            Main main = null;
            try {
                main = fromJson(jsonArray.getJSONObject(i));
                if (main != null) {
                    mainList.add(main);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return mainList;
    }
}
