package com.cs4sample.authentication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4sample.authentication.database.DatabaseSchema;
import com.cs4sample.authentication.views.NonSwipeableViewPager;
import com.cs4sample.authentication.R;
import com.cs4sample.authentication.adapters.DetailFragmentsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity
        implements View.OnClickListener {
    private NonSwipeableViewPager nonSwipeableViewPager;
    private TextView mNextTextView;
    private DetailFragmentsPagerAdapter adapter;
    private int mFragmentPosition;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // find layout views
        nonSwipeableViewPager = findViewById(R.id.detailFragmentViewPager);
        mNextTextView = findViewById(R.id.nextTextView);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }        adapter = new DetailFragmentsPagerAdapter(getSupportFragmentManager(), 0);
        nonSwipeableViewPager.setAdapter(adapter);
        nonSwipeableViewPager.setCurrentItem(0);
        // set on click listener
        mNextTextView.setOnClickListener(this);
        // set tootbar title
        updateToolbarTitle();


    }

    @Override
    public void onClick(View v) {
        if (v == mNextTextView) {
            mFragmentPosition = nonSwipeableViewPager.getCurrentItem();
            navigateForwardViewPagerFragments(mFragmentPosition);
        }
    }

    @Override
    public void onBackPressed() {
        mFragmentPosition = nonSwipeableViewPager.getCurrentItem();
        navigateBackViewPagerFragments(mFragmentPosition);
    }

    private void navigateForwardViewPagerFragments(int position) {
        if (nonSwipeableViewPager.getCurrentItem() < 4) {
            nonSwipeableViewPager.setCurrentItem(position + 1);
        }
    }

    private void navigateBackViewPagerFragments(int position) {
        if (nonSwipeableViewPager.getCurrentItem() > 0) {
            nonSwipeableViewPager.setCurrentItem(position - 1);
        }
    }

    private void updateToolbarTitle() {
        mFragmentPosition = nonSwipeableViewPager.getCurrentItem();
        mToolbar.setTitle(adapter.getPageTitle(mFragmentPosition));

    }


}
