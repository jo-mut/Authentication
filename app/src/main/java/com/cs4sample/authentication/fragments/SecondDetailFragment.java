package com.cs4sample.authentication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.activities.DetailActivity;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Gender;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondDetailFragment extends Fragment {
    //database manager
    private DatabaseManager mDatabaseManager;
    // spinner views
    private Spinner mGenderSpinner;
    private Spinner mCoutrySpinner;
    private Spinner mOrganisationSpinner;
    private Spinner mContributorSpinner;
    private  Spinner mInsuranceTypeSpinner;

    // spinner items
    private List<Gender> genders = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<String> organisations = new ArrayList<>();
    private List<String> contributions = new ArrayList<>();
    private List<String> insuranceType = new ArrayList<>();



    public SecondDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_detail, container, false);
        mGenderSpinner = view.findViewById(R.id.detailSpinner);
        mCoutrySpinner = view.findViewById(R.id.countriesSpinner);
        mOrganisationSpinner = view.findViewById(R.id.organisationSpinner);
        mContributorSpinner = view.findViewById(R.id.contributorSpinner);
        mInsuranceTypeSpinner = view.findViewById(R.id.insuranceTypepinner);

        // initialize classes
        mDatabaseManager = new DatabaseManager(getContext());

        setGenderItems();
        setInsuranceTypeSpinner();
        setContributorSpinner();
        setCountriesSpinner();
        setOrganisationSpinner();

        return view;
    }

    private void setGenderItems() {
        genders = mDatabaseManager.getGenderList();
        ArrayAdapter adapter = new ArrayAdapter<Gender>(getContext(), R.layout.spinner_item_selected, genders);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mGenderSpinner.setAdapter(adapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCountriesSpinner() {
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_selected, countries);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mCoutrySpinner.setAdapter(adapter);
        mCoutrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setOrganisationSpinner() {
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_selected, organisations);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mOrganisationSpinner.setAdapter(adapter);
        mOrganisationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setContributorSpinner() {
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_selected, contributions);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mContributorSpinner.setAdapter(adapter);
        mContributorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setInsuranceTypeSpinner() {
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_selected, insuranceType);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mInsuranceTypeSpinner.setAdapter(adapter);
        mInsuranceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
