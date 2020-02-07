package com.cs4sample.authentication.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.cs4sample.authentication.fragments.ConfirmFragment;
import com.cs4sample.authentication.fragments.FirstDetailFragment;
import com.cs4sample.authentication.fragments.SecondDetailFragment;
import com.cs4sample.authentication.fragments.ThirdDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailFragmentsPagerAdapter extends FragmentStatePagerAdapter {

    public DetailFragmentsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirstDetailFragment detailFragment = new FirstDetailFragment();
                return detailFragment;
            case 1:
                SecondDetailFragment secondDetailFragment = new SecondDetailFragment();
                return  secondDetailFragment;
            case 2:
                ThirdDetailFragment thirdDetailFragment = new ThirdDetailFragment();
                return thirdDetailFragment;
            case 3:
                ConfirmFragment confirmFragment = new ConfirmFragment();
                return  confirmFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "First";
            case 1:
                return "Second";
            case 2:
                return "Third";
            case 3:
                return "Fourth";
        }
        return null;
    }
}
