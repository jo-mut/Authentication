package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.PlayersAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    // activity views
    private EditText nameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    // database manager
    private DatabaseManager mDatabaseManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize database manager
        mDatabaseManager = new DatabaseManager(this);
        // find fragment views
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
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
                authenticateCredentials(username, password);
                break;

        }
    }

    private void authenticateCredentials(String username, String password) {
//        signInButton.setBackgroundResource(R.drawable.ripple_effect_login_button);
//        User user = mDatabaseManager.getUser(username, password);
//        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
//            Toast.makeText(this, "Authentication failed! Please check" +
//                    " your email and password again", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    signInButton.setBackgroundResource(R.drawable.login_button_background);
//                }
//            }, 1000);
//        }else {
//            nameEditText.setText("");
//            passwordEditText.setText("");
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
