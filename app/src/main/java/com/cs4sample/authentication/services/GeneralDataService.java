package com.cs4sample.authentication.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cs4sample.authentication.Constants;
import com.cs4sample.authentication.interfaces.JsonObjectListener;
import com.google.common.io.ByteStreams;

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

public class GeneralDataService {
    private static HttpURLConnection urlConnection = null;
    private static JSONObject jsonObject = null;



    public static class GeneralTask extends AsyncTask<Void, Void, Void> {
        private String mToken;
        private JsonObjectListener jsonObjectListener;
        public static String progressMessage = "";
        private static WeakReference<Context> mContext;


        public GeneralTask(Context context, String token) {
            this.mToken = token;
            mContext = new WeakReference<>(context);
            jsonObjectListener = (JsonObjectListener) mContext.get();
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
            jsonObjectListener.objectListener(jsonObject);
        }


    }

    // return username and the password the successuflly logged in the user
    private static void parseGeneralJsonObject(String token){
        String address = Constants.BASE_URL + Constants.GENERAL_DATA;
        Log.d("json address", address);

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
            jsonObject = new JSONObject(data);
            Log.d("json obj", jsonObject.toString());


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


}
