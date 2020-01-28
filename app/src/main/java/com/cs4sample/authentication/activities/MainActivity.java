package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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
    private static boolean mRemember;
    private static boolean mFirstLogin;
    private static boolean mSuccess;


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
        mFirstLogin = mSharedPreferences.getBoolean("firstLogin", true);

        // find fragment views
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        mCheckBox = findViewById(R.id.rememberCheckBox);
        // initialize click listeners
        signInButton.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);

        if (mCheckBox.isChecked()){
            mRemember = true;
        }

        if (mRemember && !mFirstLogin){
            mUsername = mSharedPreferences.getString("username", null);
            mPassword = mSharedPreferences.getString("password", null);
            nameEditText.setText(mUsername);
            passwordEditText.setText(mPassword);
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.signInButton:
                if (!mRemember) {
                    if (mFirstLogin) {
                        mUsername = nameEditText.getText().toString().trim();
                        mPassword = passwordEditText.getText().toString().trim();
                    }else {
                        mUsername = nameEditText.getText().toString().trim();
                        mPassword = passwordEditText.getText().toString().trim();
                    }
                }else {
                    if (mFirstLogin) {
                        mUsername = nameEditText.getText().toString().trim();
                        mPassword = passwordEditText.getText().toString().trim();
                    }
                }
                new AuthTask(MainActivity.this).execute();
                nameEditText.setText("");
                passwordEditText.setText("");
//                signInButton.setBackgroundResource(R.drawable.ripple_effect_login_button);
                break;

        }

        if (id == R.id.rememberCheckBox) {
            if (mCheckBox.isChecked()) {
                mRemember = true;
            }else {
                mRemember = false;
            }
        }
    }


    public static class AuthTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        private AuthTask(Context context) {
            this.mContext = context;
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

                String address = "https://ciw.cs4africa.com/democmu/Administration/Login/Submit";

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

                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.d("response line", line);
                    parseJsonObect(line, mContext);
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
            loginUser(mContext);
        }
    }


    private static void loginUser(Context context) {
        if (mSuccess){
            Toast.makeText(context, "Authentication success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setNamePasswordToPrefereces(String username, String password) {
        mEditor.putString("username", username);
        mEditor.putString("password", password);
        mEditor.commit();
    }

    public static void parseJsonObect(String line, Context context)  {
        try {
          JSONObject mainJsonObject = new JSONObject(line);
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
          if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
              mSuccess = true;
              if (mFirstLogin) {
                  mEditor.putBoolean("firstLogin", false);
                  mEditor.commit();
              }
      }else {
              mSuccess = false;
          }

          if (mRemember) {
              setNamePasswordToPrefereces(mUsername, mPassword);
          }else {
              setNamePasswordToPrefereces(mUsername, mPassword);
          }

      }catch (JSONException e) {
          e.printStackTrace();
      }
        loginUser(context);

    }
}
