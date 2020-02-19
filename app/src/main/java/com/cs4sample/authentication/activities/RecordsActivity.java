package com.cs4sample.authentication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.RecordArrayAdapter;
import com.cs4sample.authentication.database.DatabaseManager;
import com.cs4sample.authentication.models.Record;

import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private ListView mRecordsListView;
    private Toolbar mToolbar;

    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        mRecordsListView =  findViewById(R.id.recordsListView);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Records");


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_forms) {
            Intent intent = new Intent(this, DetailsPersonalActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_records, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseManager = new DatabaseManager(this);

        RecordArrayAdapter adapter = new RecordArrayAdapter(this, getRegRecords());
        mRecordsListView.setAdapter(adapter);
    }



    private List<Record> getRegRecords() {
        return mDatabaseManager.getRegData();
    }
}
