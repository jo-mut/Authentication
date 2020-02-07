package com.cs4sample.authentication.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cs4sample.authentication.Constants;
import com.cs4sample.authentication.interfaces.AsyncTaskListener;
import com.cs4sample.authentication.models.Gender;
import com.cs4sample.authentication.models.Main;
import com.cs4sample.authentication.models.Proffession;
import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GeneralDataService {
    private static HttpURLConnection urlConnection = null;
    private static WeakReference<Context> mContext;


    public static class GeneralTask extends AsyncTask<Void, Void, Void> {
        private String mToken;
        public static JSONArray mMainList  = new JSONArray();
        public static List<Proffession> proffessions = new ArrayList<>();
        public static List<Gender> mGenderList = new ArrayList<>();

        private AsyncTaskListener mListener;
        public static String progressMessage = "";


        public GeneralTask(Context context, String token) {
            this.mToken = token;
            mContext = new WeakReference<>(context);
            mListener = (AsyncTaskListener) context;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            progressMessage = "Getting professions";
            parseGeneralJsonObject(mToken);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.updateResult(proffessions);
        }


    }

    // return username and the password the successuflly logged in the user
    private static void parseGeneralJsonObject(String token){
        String address = Constants.BASE_URL + Constants.GENERAL_DATA;
        JSONObject mainJsonObject = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            String data = new String(ByteStreams.toByteArray(in));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                String curr=new String(buffer, "UTF-8");
                data+=curr;
            }
            mainJsonObject = new JSONObject(data);
            getProffessionList(mainJsonObject);
            getGenderList(mainJsonObject);

        }catch (MalformedURLException mal) {
            mal.printStackTrace();
        }catch (ProtocolException pr){
            pr.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject writeJsonObjectToFile(Context context, JSONObject jsonObject) {
        /*out put the json object into a file*/
        File file = new File(context.getFilesDir(), "Auth" + File.separator + "Json");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "general_object");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(jsonObject.toString());
            writer.flush();
            writer.close();
        } catch (Exception e)
        { }

        return jsonObject;
    }

    private static List<Proffession> getProffessionList(JSONObject object) {
        List<Proffession> proffessions = new ArrayList<>();

        try {
            JSONArray jsonArray = object.getJSONObject("Result").getJSONArray("ProffessionList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Proffession proffession = new Proffession();
                proffession.set$id(jsonObject.getString("$id"));
                proffession.setId(jsonObject.getString("Id"));
                proffession.setCode(jsonObject.getString("Code"));
                proffession.setName(jsonObject.getString("Name"));
                GeneralTask.proffessions.add(proffession);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return proffessions;
    }

    private static List<Gender> getGenderList(JSONObject object) {
        List<Gender> genderLists = new ArrayList<>();
        try {
            JSONArray jsonArray = object.getJSONObject("Result").getJSONArray("GenderList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject genderObject = jsonArray.getJSONObject(i);
                Gender gender = new Gender();
                gender.setId(genderObject.getInt("Id"));
                gender.setGenderCharacter(genderObject.getString("GenderCharacter"));
                gender.setGenderName(genderObject.getString("GenderName"));
                GeneralTask.mGenderList.add(gender);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return genderLists;
    }
}
