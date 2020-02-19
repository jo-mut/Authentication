package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Forms;

import java.util.ArrayList;
import java.util.List;

public class DetailsSelectionActivity extends AppCompatActivity
        implements View.OnClickListener{
    //database manager
    private DatabaseManager mDatabaseManager;
    // spinner views
    private Spinner mGenderSpinner;
    private Spinner mCoutrySpinner;
    private Spinner mOrganisationSpinner;
    private Spinner mContributorSpinner;
    private Spinner mInsuranceTypeSpinner;
    private Button mNextButton;
    private Button mPreviousButton;
    private Toolbar mToolbar;

    // spinner items
    private List<String> mGenders = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<String> organisations = new ArrayList<>();
    private List<String> contributions = new ArrayList<>();
    private List<String> insuranceType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_selection);

        mGenderSpinner = findViewById(R.id.genderSpinner);
        mCoutrySpinner = findViewById(R.id.countriesSpinner);
        mOrganisationSpinner = findViewById(R.id.organisationSpinner);
        mContributorSpinner = findViewById(R.id.contributorSpinner);
        mInsuranceTypeSpinner = findViewById(R.id.insuranceTypeSpinner);
        mNextButton = findViewById(R.id.nextButton);
        mPreviousButton = findViewById(R.id.previousButton);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        // initialize classes
        mDatabaseManager = new DatabaseManager(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        setGenderItems();
        setContributorSpinner();
        setCountriesSpinner();
        setOrganisationSpinner();
        setInsuranceTypeSpinner();
    }

    @Override
    public void onClick(View v) {
        if (v == mNextButton) {
            Intent intent = new Intent(this, DetailsPhotosActivity.class);
            startActivity(intent);
        }

        if (v == mPreviousButton) {
            finish();
        }
    }

    private void setGenderItems() {
        mGenders = mDatabaseManager.getGenderList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected, mGenders);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Forms.gender =parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCountriesSpinner() {
        countries = mDatabaseManager.getCoutryList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected, countries);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCoutrySpinner.setAdapter(adapter);
        mCoutrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Forms.country =parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setOrganisationSpinner() {
        organisations = mDatabaseManager.getEmployerOrgans();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected, organisations);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mOrganisationSpinner.setAdapter(adapter);
        mOrganisationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Forms.employerOrganisation =parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setContributorSpinner() {
        contributions = mDatabaseManager.getContributorList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected, contributions);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mContributorSpinner.setAdapter(adapter);
        mContributorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Forms.contributionPayer =parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setInsuranceTypeSpinner() {
        insuranceType = mDatabaseManager.getTypeOfInsuranceList();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_selected, insuranceType);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mInsuranceTypeSpinner.setAdapter(adapter);
        mInsuranceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Forms.insuranceType =parent.getItemAtPosition(position).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
