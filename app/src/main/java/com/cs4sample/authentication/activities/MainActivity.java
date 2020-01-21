package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.MainObject;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.models.User;
import com.cs4sample.authentication.models.UserAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    // activity views
    private EditText nameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private CheckBox mCheckBox;
    // database manager
    private DatabaseManager mDatabaseManager;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static  String mUsername = "";
    private static  String mPassword = "";
    private boolean mRemember = false;
    private boolean mFirstLogin = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize database manager
        mDatabaseManager = new DatabaseManager(this);
        // initialize shared preferences
        mSharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        // read shared preferences
        mFirstLogin = mSharedPreferences.getBoolean("firstLogin", false);
        mRemember = mSharedPreferences.getBoolean("remember", false);
        mUsername = mSharedPreferences.getString("username", null);
        mPassword = mSharedPreferences.getString("password", null);
        // find fragment views
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        mCheckBox = findViewById(R.id.rememberCheckBox);
        // initialize click listeners
        signInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.signInButton:
                String username = nameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                mPassword = password;
                mUsername = username;
                authenticateCredentials();
                break;

        }
    }

    private void authenticateCredentials() {
        signInButton.setBackgroundResource(R.drawable.ripple_effect_login_button);
        new AuthTask().execute();

    }

    private void logInAuthenticatedUser(String username, String password) {
        User user = mDatabaseManager.getUser(username, password);
        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            Toast.makeText(this, "Authentication failed! Please check" +
                    " your email and password again", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    signInButton.setBackgroundResource(R.drawable.login_button_background);
                }
            }, 1000);
        }else {
            nameEditText.setText("");
            passwordEditText.setText("");
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    public static class AuthTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

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

                String address = "https://ciw.cs4africa.com/democmu/Administration/Login/Submit";
                String authString = mUsername + ":" + mPassword;
                String encoded = Base64.encodeToString(authString.getBytes(), Base64.DEFAULT);  //Java 8

                URL url = new URL(address);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
              //  urlConnection.setRequestProperty("Authorization", "Basic "+encoded);
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");

//                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                urlConnection.setChunkedStreamingMode(0);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(mainObject.toString());
                writer.flush();

                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.d("response token", urlConnection.getHeaderField("authorization"));
                    parseJsonObect(line);

                }

                ObjectInputStream obj = new ObjectInputStream(urlConnection.getInputStream());
                Log.d("json obj", obj.toString());
                Object object = obj.readObject();
                Log.d("json object", object.toString());


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }
    }

    private void loginSharedPreferences() {
        if (mFirstLogin) {
            logInAuthenticatedUser(mUsername, mPassword);
        }else {
            if (mRemember) {
                mEditor.putBoolean("remember", mRemember);
                nameEditText.setText(mUsername);
                passwordEditText.setText(mPassword);
                logInAuthenticatedUser(mUsername, mPassword);
            }else {
                logInAuthenticatedUser(mUsername, mPassword);
            }
        }


    }

    public static void parseJsonObect(String line)  {
      try {
          JSONObject mainJsonObject = new JSONObject(line);
          Log.d("auth response line", line);
          Log.d("auth response object", mainJsonObject.toString());
//                String id = jsonObject.getString("id");
//                String module_name = jsonObject.getString("ModuleName");
          String firstObect = mainJsonObject.getString("Result");
//                JSONObject resultObject = new JSONObject(firstObect);
//                String login_id = jsonObject.getString("id");
//                String isOkay = jsonObject.getString("IsOkay");
//                String message = jsonObject.getString("Message");
        JSONObject firstJsonObject = new JSONObject(firstObect);
        String secondObject = firstJsonObject.getString("Result");
//                String tb_id = secondObject.getString("id");
//                String user_id = secondObject.getString("user_id");
          JSONObject secondJsonObject = new JSONObject(secondObject);
          String username = secondJsonObject.getString("username");
          String password = secondJsonObject.getString("password");
//                String full_name = secondObject.getString("full_name");
//                String account_id = secondObject.getString("account_id");
//                String user_type = secondObject.getString("user_type_id");
//                String status = secondObject.getString("status");
//                String change_password = secondObject.getString("changed_password");

      }catch (JSONException e) {
          e.printStackTrace();
      }

    }

}
