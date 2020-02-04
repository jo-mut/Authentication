package com.cs4sample.authentication.services;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.cs4sample.authentication.Constants;
import com.cs4sample.authentication.models.GeneralData;
import com.cs4sample.authentication.models.Main;
import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralDataService {
    private static HttpURLConnection urlConnection = null;

    public static class GeneralTask extends AsyncTask<Void, Void, Void> {
        private String mToken;
        public static JSONArray mMainList  = new JSONArray();

        public GeneralTask(Context context, String token) {
            this.mToken = token;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            parseGeneralJsonObject(mToken);
//            Log.d("general data list", mMainList.length() + "");
            Log.d("general token", mToken);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    // return username and the password the successuflly logged in the user
    private static void parseGeneralJsonObject(String token){
        String address = Constants.BASE_URL + Constants.GENERAL_DATA;

        JSONObject mainJsonObject = null;
        Log.d("successful address", address);
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
//            int httpResult = urlConnection.getResponseCode();
//            if (httpResult != HttpURLConnection.HTTP_OK) {
//                Log.d("response error", urlConnection.getResponseMessage());
//            }
            String data = new String(ByteStreams.toByteArray(in));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                String curr=new String(buffer, "UTF-8");
                data+=curr;
                Log.d("main json object", curr);

            }
            Log.d("response json", data + "");
            getMainJsonObject(data);
//            Log.d("main json object", mainJsonObject.toString());

        }catch (MalformedURLException mal) {
            mal.printStackTrace();
        }catch (ProtocolException pr){
            pr.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject getMainJsonObject(String line) {
        JSONObject mainJsonObject = null;
        try {
            mainJsonObject = new JSONObject(line);
            Log.d("main json object", mainJsonObject.toString());

            /*out put the json object into a file*/
//            File file = new File(context.getFilesDir(), "Auth" + File.separator + "Json");
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            try {
//                File gpxfile = new File(file, "general_object");
//                FileWriter writer = new FileWriter(gpxfile);
//                writer.append(mainJsonObject.toString());
//                writer.flush();
//                writer.close();
//            } catch (Exception e) { }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return mainJsonObject;
    }

    // return username and the password the successuflly logged in the user
    private static void parseJsonObect(String line)  {

        try {
            JSONObject mainJsonObject = new JSONObject(line);
            Log.d("main json object", mainJsonObject.toString());

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
