package com.cs4sample.authentication.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cs4sample.authentication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstDetailFragment extends Fragment {
    private EditText mBirthDateEditText;
    private AlertDialog alertDialog = null;


    public FirstDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_detail, container, false);
        mBirthDateEditText = view.findViewById(R.id.dateOfBirthEditText);


        mBirthDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

        return view;
    }


    private void showDatePicker() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_date_picker, null);
        final DatePicker mDatePicker = view.findViewById(R.id.datePicker);
        Button setDateButton = view.findViewById(R.id.setDateButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBirthDateEditText.setText(mDatePicker.getDayOfMonth()  +"/"
                        + mDatePicker.getMonth() + "/" + mDatePicker.getMonth() );
                alertDialog.dismiss();

            }
        });
    }


}
