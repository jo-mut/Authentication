package com.cs4sample.authentication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs4sample.authentication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdDetailFragment extends Fragment {


    public ThirdDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_detail, container, false);

        return view;
    }

}
