package com.cs4sample.authentication.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4sample.authentication.HomeActivity;
import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // fragment views
    private EditText nameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    // database manager
    private DatabaseManager mDatabaseManager;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // initialize database manager
        mDatabaseManager = new DatabaseManager(getContext());
        // find fragment views
        nameEditText = view.findViewById(R.id.nameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        signInButton = view.findViewById(R.id.signInButton);
        // initialize click listeners
        signInButton.setOnClickListener(this);

        return view;
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
        User user = mDatabaseManager.getUser(username, password);

        if (!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            Toast.makeText(getContext(), "Authentication failed! Please check" +
                            " your email and password again", Toast.LENGTH_SHORT).show();
        }else {
            nameEditText.setText("");
            passwordEditText.setText("");
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }
    }

}
