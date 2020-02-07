package com.cs4sample.authentication.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.interfaces.AsyncTaskListener;
import com.cs4sample.authentication.interfaces.ProfessionsAlert;
import com.cs4sample.authentication.models.Proffession;
import com.cs4sample.authentication.services.GeneralDataService;

import java.util.ArrayList;
import java.util.List;

public class GeneralArrayAdapter extends BaseAdapter {
    private Context mContext;
    private static List<Proffession> mProffessions = new ArrayList<>();
    private ProfessionsAlert mProfessionsAlert;


    public GeneralArrayAdapter(Context context, List<Proffession> proffessions ) {
        this.mContext = context;
        mProffessions = proffessions;
        mProfessionsAlert = (ProfessionsAlert) mContext;

    }


    @Override
    public long getItemId(int position) {
        String id = mProffessions.get(position).getId();
        return (long) Integer.parseInt(id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Proffession proffession = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_general_data, parent, false);
        }

        ImageView profileImageView = convertView.findViewById(R.id.profileImageView);
        TextView codeTextView = convertView.findViewById(R.id.codeTextView);
        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);


        profileImageView.setImageBitmap(null);
        codeTextView.setText("Code: " + proffession.getCode());
        nameTextView.setText( "Name: " + proffession.getName());
        idTextView.setText("Id: " + proffession.getId());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfessionsAlert.showData(proffession);
            }
        });

        return convertView;
    }

    @Nullable
    @Override
    public Proffession getItem(int position) {
        return mProffessions.get(position);
    }

    @Override
    public int getCount() {
        return mProffessions.size();
    }
}
