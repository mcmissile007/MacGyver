package com.falkenapps.macgyver.search.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.search.fragments.GuardSearchFragment;
import com.falkenapps.macgyver.search.fragments.JobSearchFragment;
import com.falkenapps.macgyver.search.fragments.LocationSearchFragment;
import com.falkenapps.macgyver.search.fragments.QuestionSearchFragment;
import com.falkenapps.macgyver.search.fragments.SearchSlidePageFragment;

/**
 * Created by FalkenApps:falken on 5/4/17.
 */

public class SearchPageFragmentAdapter extends FragmentPagerAdapter {

    private final Context context;
    private String[] tabTitles;
    private ViewPager viewPager;


    public SearchPageFragmentAdapter(FragmentManager fm, Context context, ViewPager viewPager) {
        super(fm);
        this.context = context;
        tabTitles= new String[]{context.getResources().getString(R.string.search_fragment_job),
                context.getResources().getString(R.string.search_fragment_location),
                context.getResources().getString(R.string.search_fragment_guard),
                context.getResources().getString(R.string.search_fragment_question)};
        this.viewPager = viewPager;
        Log.d(Constant.TAG,"SearchPageFragmentAdapter constructor");
    }

    @Override
    public Fragment getItem(int position) {
        //return SearchSlidePageFragment.newInstance(position);
        if (position == 0){
            return JobSearchFragment.newInstance(position);

        }
        else if (position == 1){
            return LocationSearchFragment.newInstance(position);
        }
        else if (position == 2){
            return GuardSearchFragment.newInstance(position);
        }
        else if (position == 3){
            return QuestionSearchFragment.newInstance(position);
        }
        else{
            return null; // error in position
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Log.d(Constant.TAG,"getPageTitle:" + position);
        return tabTitles[position];
    }
}
