package com.falkenapps.macgyver.main.adapters;


/*
Created by FalkenApps: Jorge Bareas on May 20016
*/

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.main.fragments.PageFragment;


public class PFAdapter extends FragmentPagerAdapter {

    private final Context context;
    private static final String ACTIVITY_TAG = Constant.TAG + ":PFAdapter";
    private String[] tabTitles;

    public PFAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles= new String[]{context.getResources().getString(R.string.main_tab0_name), context.getResources().getString(R.string.main_tab1_name),context.getResources().getString(R.string.main_tab2_name)};
        Log.d(ACTIVITY_TAG,"PFAdapter constructor");

    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        Log.d(ACTIVITY_TAG,"getItem:" + position);

        return PageFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Log.d(ACTIVITY_TAG,"getPageTitle:" + position);
        return tabTitles[position];
    }
}
