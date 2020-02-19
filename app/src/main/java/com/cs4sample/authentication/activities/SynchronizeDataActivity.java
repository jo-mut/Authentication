package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.interfaces.JsonObjectListener;
import com.cs4sample.authentication.models.ContributionPayer;
import com.cs4sample.authentication.models.Country;
import com.cs4sample.authentication.models.EmployerOrgan;
import com.cs4sample.authentication.models.Gender;
import com.cs4sample.authentication.models.Insurance;
import com.cs4sample.authentication.models.Proffession;
import com.cs4sample.authentication.services.GeneralDataService;
import com.cs4sample.authentication.services.LoginService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SynchronizeDataActivity extends AppCompatActivity
        implements JsonObjectListener {
    private DatabaseManager mDatabaseManager;
    private static JSONObject jsonObject;
    public static List<Proffession> mProffessions = new ArrayList<>();
    public static List<Gender> mGenderList = new ArrayList<>();
    public static List<ContributionPayer> mContributorList = new ArrayList<>();
    public static List<EmployerOrgan> mEmployerOrganisations = new ArrayList<>();
    public static List<Country> mCoutriesList = new ArrayList<>();
    public static List<Insurance> mInsuranceTypes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_data);
        new GeneralDataService.GeneralTask(this, LoginService.AUTH_TOKEN).execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseManager = new DatabaseManager(this);
    }

    @Override
    public void objectListener(JSONObject object) {
        Log.d("database save ", object.toString() );
        new ReadDBTask(this).execute(object);

    }

    public static class ReadDBTask extends AsyncTask<JSONObject, Integer, Void> {
        private static WeakReference<Context> mContext;

        private ReadDBTask(Context context) {
            mContext = new WeakReference<>(context);
            Log.d("database save ", "called" );

        }

        @Override
        protected Void doInBackground(JSONObject... objects) {
            Log.d("database save ", objects[0] + "");

            try {
                JSONArray gender = objects[0].getJSONObject("Result").getJSONArray("GenderList");
                JSONArray countries = objects[0].getJSONObject("Result").getJSONArray("CountriesList");
                JSONArray contributors = objects[0].getJSONObject("Result").getJSONArray("ContributionPayersList");
                JSONArray insurances = objects[0].getJSONObject("Result").getJSONArray("InsuranceTypeList");
                JSONArray organs = objects[0].getJSONObject("Result").getJSONArray("EmployerOrganList");

                getJsonGenderList(gender);
                getJsonCountryList(countries);
                getJsonContributorList(contributors);
                getJsonInsuranceTypes(insurances);
                getJsonEmployerOrganizations(organs);

            }catch (Exception e) {
                e.printStackTrace();
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
            Intent intent = new Intent(mContext.get(), DetailsPersonalActivity.class);
            mContext.get().startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }

    /**save the gender list for local persistence*/
    private static void saveGenderList() {
        DatabaseManager.saveGenderList(mGenderList);
    }

    private static void saveCountryList() {
        DatabaseManager.saveCountries(mCoutriesList);
    }

    private static void saveEmployerOrgans() {
        DatabaseManager.saveEmployerOrgans(mEmployerOrganisations);
    }

    private static void saveContributorPayer() {
        DatabaseManager.saveContributors(mContributorList);
    }

    private static void saveInsuranceTypes() {
        DatabaseManager.saveInsuranceTypes(mInsuranceTypes);
    }

    private static void getJsonProffessionList(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Proffession proffession = new Proffession();
                proffession.setId(jsonObject.getString("Id"));
                proffession.setCode(jsonObject.getString("Code"));
                proffession.setName(jsonObject.getString("Name"));
                mProffessions.add(proffession);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**get a list of gender from the json object*/
    private static void getJsonGenderList(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Gender gender = new Gender();
                gender.setId(jsonObject.getInt("Id"));
                gender.setGenderCharacter(jsonObject.getString("GenderCharacter"));
                gender.setGenderName(jsonObject.getString("GenderName"));
                mGenderList.add(gender);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        saveGenderList();
    }


    private static void getJsonCountryList(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country();
                country.setId(jsonObject.getString("Id"));
                country.setCode(jsonObject.getString("Code"));
                country.setName(jsonObject.getString("Country"));
                mCoutriesList.add(country);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        saveCountryList();
    }

    private static void getJsonEmployerOrganizations(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EmployerOrgan employerOrgan = new EmployerOrgan();
                employerOrgan.setId(jsonObject.getString("Id"));
                employerOrgan.setCode(jsonObject.getString("Code"));
                employerOrgan.setName(jsonObject.getString("Name"));
                employerOrgan.setCommune(jsonObject.getString("Commune"));
                employerOrgan.setInsuranceId(jsonObject.getString("InsuranceId"));
                mEmployerOrganisations.add(employerOrgan);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        saveEmployerOrgans();
    }

    private static void getJsonInsuranceTypes(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Insurance insurance = new Insurance();
                insurance.setId(jsonObject.getString("Id"));
                insurance.setCode(jsonObject.getString("Code"));
                insurance.setContributorId(jsonObject.getString("ContributorId"));
                insurance.setType(jsonObject.getString("Type"));
                mInsuranceTypes.add(insurance);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        saveInsuranceTypes();
    }

    private static void getJsonContributorList(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ContributionPayer contributionPayer = new ContributionPayer();
                contributionPayer.setId(jsonObject.getString("Id"));
                contributionPayer.setCode(jsonObject.getString("Code"));
                contributionPayer.setName(jsonObject.getString("Name"));
                mContributorList.add(contributionPayer);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        saveContributorPayer();
    }

}
