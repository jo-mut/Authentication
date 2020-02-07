package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.services.GeneralDataService;

public class SynchronizeDataActivity extends AppCompatActivity {
    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize_data);

        mDatabaseManager = new DatabaseManager(this);
        new SychronizeAsyncTask().execute();
    }

    public class SychronizeAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            saveGenderList();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SynchronizeDataActivity.this, DetailActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void saveGenderList() {
        mDatabaseManager.getGenderList(GeneralDataService.GeneralTask.mGenderList);
    }
}
