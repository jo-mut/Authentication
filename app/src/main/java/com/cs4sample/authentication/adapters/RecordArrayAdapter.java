package com.cs4sample.authentication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs4sample.authentication.R;
import com.cs4sample.authentication.activities.RecordDetailActivity;
import com.cs4sample.authentication.models.Proffession;
import com.cs4sample.authentication.models.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordArrayAdapter extends BaseAdapter {
    private Context mContext;
    private List<Record> mRecords = new ArrayList<>();
    private static final String ID_NUMBER = "ID";


    public RecordArrayAdapter(Context mContext, List<Record> records) {
        this.mContext = mContext;
        this.mRecords = records;
    }

    @Override
    public int getCount() {
        return mRecords.size();
    }

    @Override
    public Record getItem(int position) {
        return mRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Record record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_records, parent, false);
        }

        ImageView profileImageView = convertView.findViewById(R.id.profileImageView);
        TextView surnameTextView = convertView.findViewById(R.id.nameTextView);
        TextView idNumberTextView = convertView.findViewById(R.id.idTextView);
        TextView birthDateTextView = convertView.findViewById(R.id.dateOfBirthTextView);

        if (record.getProfilePhoto() != null) {
            byte[] imageBytes = Base64.decode(record.getProfilePhoto(), Base64.DEFAULT);
            String decodedPath = new String(imageBytes);
            Bitmap bitmap = BitmapFactory.decodeFile(decodedPath);
            profileImageView.setImageBitmap(bitmap);
        }

        birthDateTextView.setText("Birth Date: " + record.getDateOfBirth());
        surnameTextView.setText(record.getSurname());
        idNumberTextView.setText("Id Number: " + record.getIdNumber());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecordDetails(record.getIdNumber());
            }
        });

        return convertView;
    }


    private void viewRecordDetails(String id) {
        Intent intent = new Intent(mContext, RecordDetailActivity.class);
        intent.putExtra(RecordArrayAdapter.ID_NUMBER, id);
        mContext.startActivity(intent);
    }
}
