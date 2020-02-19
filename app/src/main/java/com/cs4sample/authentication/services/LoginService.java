package com.cs4sample.authentication.services;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.cs4sample.authentication.Constants;
import com.cs4sample.authentication.interfaces.LoginListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginService  {
    public static Map<String, String> loginMap = new HashMap<>();
    private static HttpURLConnection urlConnection = null;
    public static String AUTH_TOKEN = "";

    public static class AuthTask extends AsyncTask<Void, Void, Void> {
        private String mUsername;
        private String mPassword;
        private LoginListener loginListener;
        private static WeakReference<Context> mContext;


        public AuthTask(Context context, String username, String password) {
            mContext = new  WeakReference<>(context);
            this.mUsername = username;
            this.mPassword = password;
            loginListener = (LoginListener) mContext.get();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("response username", mUsername);
            Log.d("response username", mPassword);


            HttpURLConnection urlConnection = null;
            try {

                JSONObject userObject = new JSONObject();
                userObject.put("UserName", mUsername);
                userObject.put("PassWord", mPassword);
                userObject.put("Language", "French");
                userObject.put("AccountName", "CMU DEMO");
                userObject.put("Branch", "IDCAPTURE");

                JSONObject mainObject = new JSONObject();
                mainObject.put("IsRenewalPasswordRequest", "false");
                mainObject.put("CurrentUser", userObject);

                String address = Constants.BASE_URL +  Constants.AUTH_URL;

                URL url = new URL(address);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(mainObject.toString());
                writer.flush();

                String token = urlConnection.getHeaderField("Authorization");
                AUTH_TOKEN = token;

                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.d("response line", line);
                    parseJsonObect(line, mUsername, mPassword);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loginListener.loginListener(AUTH_TOKEN);

        }
    }

    public static LoginListener setLoginlListener(LoginListener loginlListener) {
        return loginlListener;
    }

    // return username and the password the successuflly logged in the user
    private static Map<String, String> parseJsonObect(String line, String name, String pass)  {

        try {
            JSONObject mainJsonObject = new JSONObject(line);
            String firstObject = mainJsonObject.getString("Result");
            JSONObject firstJsonObject = new JSONObject(firstObject);
            String secondObject = firstJsonObject.getString("Result");
            JSONObject secondJsonObject = new JSONObject(secondObject);
            String username = secondJsonObject.getString("username");
            String password = secondJsonObject.getString("password");
            if (!TextUtils.isEmpty(username)) {
                loginMap.put("username", name);
                loginMap.put("password", pass);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return loginMap;
    }


}
