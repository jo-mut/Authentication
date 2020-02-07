package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4sample.authentication.Constants;
import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.services.LoginService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
//    private static boolean mSuccess;


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

        Log.d("successful rem", mRemember  +"");


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.signInButton:
                loginInUser();
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

    private void loginInUser() {
        if (!mRemember) {
            mUsername = nameEditText.getText().toString().trim();
            mPassword = passwordEditText.getText().toString().trim();
        }else {
            if (mFirstLogin) {
                mUsername = nameEditText.getText().toString().trim();
                mPassword = passwordEditText.getText().toString().trim();
            }
        }

        new LoginService.AuthTask(this, mUsername, mPassword).execute();
        nameEditText.setText("");
        passwordEditText.setText("");
        Log.d("successful token", LoginService.AUTH_TOKEN);

        if (!TextUtils.isEmpty(LoginService.AUTH_TOKEN)) {
            String username = LoginService.loginMap.get("username");
            String password = LoginService.loginMap.get("password");

            if (mFirstLogin){
                mEditor.putBoolean("firstLogin", false);
                mEditor.commit();
            }
            if (mRemember) {
                mEditor.putString("username", mUsername);
                mEditor.putString("password", mPassword);
                mEditor.commit();
            }
            Toast.makeText(this, "Authentication success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SynchronizeDataActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        }else {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }

    }

}
