package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {
    // activity views
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize activity views
        mFrameLayout = findViewById(R.id.login_fragment_container);

        // adding login fragment to the activity
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.login_fragment_container, loginFragment);
        ft.commit();
    }
}
