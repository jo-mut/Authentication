package com.cs4sample.authentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.models.Forms;

import java.util.Calendar;

public class DetailsPersonalActivity extends AppCompatActivity
        implements View.OnClickListener{
    private EditText mBirthDateEditText;
    private AlertDialog alertDialog = null;
    private EditText mSurnameEditText;
    private EditText mOtherNamesEditText;
    private EditText mIdNumberEditText;
    private Button mNextButton;
    private Button mPreviousButton;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_personal);

        mBirthDateEditText = findViewById(R.id.dateOfBirthEditText);
        mSurnameEditText = findViewById(R.id.surnameEditText);
        mIdNumberEditText = findViewById(R.id.idNumberEditText);
        mOtherNamesEditText =  findViewById(R.id.otherEditText);
        mNextButton = findViewById(R.id.nextButton);
        mPreviousButton = findViewById(R.id.previousButton);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mBirthDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

     

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_records) {
            Intent intent = new Intent(this, RecordsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forms, menu);
        return true;
    }

    private void showDatePicker() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_date_picker, null);
        final DatePicker mDatePicker = view.findViewById(R.id.datePicker);
        Button setDateButton = view.findViewById(R.id.setDateButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = "";
                String day = "";
                String month = "";

                if (mDatePicker.getYear() <= Calendar.getInstance().get(Calendar.YEAR)) {
                    year = String.valueOf(mDatePicker.getYear());
                }

                if (mDatePicker.getDayOfMonth() <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                    day = String.valueOf(mDatePicker.getDayOfMonth());
                }

                if (mDatePicker.getMonth() <= Calendar.getInstance().get(Calendar.MONTH)) {
                    month = String.valueOf(mDatePicker.getMonth());
                }
                mBirthDateEditText.setText(day +"/" + month + "/" + year);
                alertDialog.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mNextButton) {
            if (validateInput()) {
                Intent intent = new Intent(this, DetailsSelectionActivity.class);
                startActivity(intent);
            }
        }

        if (v == mPreviousButton) {
            finish();
        }
    }

    private boolean validateInput() {
        String surname = mSurnameEditText.getText().toString().trim();
        String id = mIdNumberEditText.getText().toString().trim();
        String otherNames = mOtherNamesEditText.getText().toString().trim();
        String date = mBirthDateEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(surname)) {
            Forms.surname = mSurnameEditText.getText().toString().trim();
        }else {
            mSurnameEditText.setError("Surname is empty");
            return false;
        }

        if (!TextUtils.isEmpty(id)) {
            if (id.toCharArray().length < 6) {
                mIdNumberEditText.setError("Id number has less than 6 characters");
                return false;
            }else if ( id.toCharArray().length > 8){
                mIdNumberEditText.setError("Id number has more than 8 characters");
                return false;
            }else {
                Forms.idNumber = mIdNumberEditText.getText().toString().trim();
            }
        }else {
            mIdNumberEditText.setError("Id number is empty");
            return false;
        }

        if (!TextUtils.isEmpty(otherNames)) {
            Forms.otherNames = mOtherNamesEditText.getText().toString().trim();
        }else {
            mOtherNamesEditText.setError("Other names is empty");
            return false;
        }

        if (!TextUtils.isEmpty(date)) {
            Forms.dateOfBirth = mBirthDateEditText.getText().toString().trim();
        }else {
            mBirthDateEditText.setError("Birth date is empty");
            return false;
        }

        return true;
    }
}
