package com.cs4sample.authentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Record;
import com.cs4sample.authentication.models.Forms;
import com.cs4sample.authentication.views.ProportionalImageView;

public class RecordDetailActivity extends AppCompatActivity {
    // views
    private TextView mBirthDateTextView;
    private TextView mOtherNamesTextView;
    private TextView mSurnameTextView;
    private TextView mIdNumberTextView;
    private TextView genderTextView;
    private TextView countryTextView;
    private TextView organisationTextView;
    private TextView contributorTextView;
    private TextView typeOfInsuranceTextView;
    private ProportionalImageView idFrontImageView;
    private ProportionalImageView idBacKImageView;
    private ProportionalImageView profileImageView;
    private Toolbar mToolbar;
    private DatabaseManager mDatabaseManager;
    private Record mRecord;
    private static final String ID_NUMBER = "ID";
    private String idNumberExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        mBirthDateTextView = findViewById(R.id.dateOfBirthTextView);
        mOtherNamesTextView = findViewById(R.id.otherTextView);
        mSurnameTextView = findViewById(R.id.surnameTextView);
        mIdNumberTextView = findViewById(R.id.idNumberTextView);
        genderTextView = findViewById(R.id.genderTextView);
        countryTextView = findViewById(R.id.countryTextView);
        organisationTextView = findViewById(R.id.organisationTextView);
        contributorTextView = findViewById(R.id.contributionTextView);
        typeOfInsuranceTextView = findViewById(R.id.typeOfInsuranceTextView);
        profileImageView = findViewById(R.id.profileImageView);
        idFrontImageView = findViewById(R.id.idFrontImageView);
        idBacKImageView = findViewById(R.id.idBackImageView);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        idNumberExtra = getIntent().getStringExtra(ID_NUMBER);
        Log.d("id_number", idNumberExtra + " ");


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseManager = new DatabaseManager(this);
        setRecords(idNumberExtra);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setRecords(String id) {
        mRecord = getRecordById(id);

        if (mRecord != null) {
            mBirthDateTextView.setText("Birth Date: " + mRecord.getDateOfBirth());
            mSurnameTextView.setText("Surname: " + mRecord.getSurname());
            mIdNumberTextView.setText("Id Number: " + mRecord.getIdNumber());
            mOtherNamesTextView.setText("Other Names: " + mRecord.getOtherNames());
            genderTextView.setText("Gender: " + mRecord.getGender());
            contributorTextView.setText("Contribution Payer: " + mRecord.getContributionPayer());
            typeOfInsuranceTextView.setText("Insurance Type: " + mRecord.getInsuranceType());
            organisationTextView.setText("Employer Organisation: " + mRecord.getEmployerOrganisation());
            countryTextView.setText("Country: " + mRecord.getCountry());

            if (mRecord.getProfilePhoto() != null) {
                byte[] imageBytes = Base64.decode(mRecord.getProfilePhoto(), Base64.DEFAULT);
                String decodedPath = new String(imageBytes);
                Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
                profileImageView.setImageBitmap(bitmap);

            }

            if (mRecord.getIdBack() != null) {
                byte[] imageBytes = Base64.decode(mRecord.getIdBack(), Base64.DEFAULT);
                String decodedPath = new String(imageBytes);
                Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
                idBacKImageView.setImageBitmap(bitmap);
            }

            if (mRecord.getIdFront() != null) {
                byte[] imageBytes = Base64.decode(mRecord.getIdFront(), Base64.DEFAULT);
                String decodedPath = new String(imageBytes);
                Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
                idFrontImageView.setImageBitmap(bitmap);
            }

        }
    }

    private Record getRecordById(String idNumber) {
        return  mDatabaseManager.getRecordById(idNumber);
    }
}
