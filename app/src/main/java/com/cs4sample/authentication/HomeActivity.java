package com.cs4sample.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {
    private Toolbar mHomeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // initialize home views
        mHomeToolbar = findViewById(R.id.homeToolbar);

    }
}
