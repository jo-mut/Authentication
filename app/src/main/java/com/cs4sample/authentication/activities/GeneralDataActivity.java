package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.GeneralArrayAdapter;
import com.cs4sample.authentication.interfaces.AsyncTaskListener;
import com.cs4sample.authentication.interfaces.ProfessionsAlert;
import com.cs4sample.authentication.models.Proffession;
import com.cs4sample.authentication.services.GeneralDataService;
import com.cs4sample.authentication.services.LoginService;

import java.util.ArrayList;
import java.util.List;

public class GeneralDataActivity extends AppCompatActivity
        implements AsyncTaskListener, ProfessionsAlert {
    private Toolbar mHomeToolbar;
    private ImageView mAddImageView;
    private ListView mGeneralDataListView;
    private ProgressBar mProgressBar;
    private LinearLayout mProgressLinearLayout;
    private TextView mProgressTextView;
    private int progression;
    private AlertDialog alertDialog = null;
    private List<Proffession> mProffessions  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_data);

        mGeneralDataListView = findViewById(R.id.generalDataListView);
        mHomeToolbar = findViewById(R.id.homeToolbar);
        mProgressLinearLayout = findViewById(R.id.progressLinearLayout);
        mProgressTextView = findViewById(R.id.progressTextView);
        mProgressBar = findViewById(R.id.progressBar);
        setSupportActionBar(mHomeToolbar);
        mHomeToolbar.setTitle("Professions");

        // execute general data service
        new GeneralDataService.GeneralTask(this, LoginService.AUTH_TOKEN).execute();
        mProgressLinearLayout.setVisibility(View.VISIBLE);
        mProgressTextView.setText(GeneralDataService.GeneralTask.progressMessage);
    }

    @Override
    public void updateResult(List<Proffession> proffessions) {
        mProffessions.addAll(proffessions);
        GeneralArrayAdapter arrayAdapter = new GeneralArrayAdapter(this, mProffessions);
        arrayAdapter.notifyDataSetChanged();
        if (mGeneralDataListView == null) {
            mGeneralDataListView = findViewById(R.id.generalDataListView);
        }
        mProgressLinearLayout.setVisibility(View.GONE);
        mGeneralDataListView.setVisibility(View.VISIBLE);
        mGeneralDataListView.setAdapter(arrayAdapter);
    }

    @Override
    public void showData(Proffession p) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_data_diolog, null);
        Button dismissButton = (Button) view.findViewById(R.id.dismissButton);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView codeTextView = view.findViewById(R.id.codeTextView);

        if (p != null) {
            nameTextView.setText("Name: " + p.getName());
            codeTextView.setText("Code: " + p.getCode());
        }

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


}
