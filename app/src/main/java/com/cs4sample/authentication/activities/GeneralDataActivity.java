package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.models.GeneralData;
import com.cs4sample.authentication.models.Main;
import com.cs4sample.authentication.models.Player;
import com.cs4sample.authentication.services.GeneralDataService;
import com.cs4sample.authentication.services.LoginService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralDataActivity extends AppCompatActivity {
    private Toolbar mHomeToolbar;
    private ImageView mAddImageView;
    private RecyclerView mGeneralDataRecyclerView;
    private List<GeneralData> generalList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_data);

        new GeneralDataService.GeneralTask(this, LoginService.AUTH_TOKEN).execute();

        getGeneralList();
    }

    private void getGeneralList() {
        JSONArray jsonArray = GeneralDataService.GeneralTask.mMainList;
//        Log.d("general results array", jsonArray.length() + "");

    }
}
