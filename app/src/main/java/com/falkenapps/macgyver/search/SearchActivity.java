package com.falkenapps.macgyver.search;

//https://developer.android.com/training/animation/screen-slide.html

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.main.fragments.PageInteractor;
import com.falkenapps.macgyver.main.fragments.PageInteractorImpl;
import com.falkenapps.macgyver.search.adapters.SearchPageFragmentAdapter;
import com.falkenapps.macgyver.search.fragments.GuardSearchFragment;
import com.falkenapps.macgyver.search.fragments.JobSearchFragment;
import com.falkenapps.macgyver.search.fragments.LocationSearchFragment;
import com.falkenapps.macgyver.search.fragments.QuestionSearchFragment;
import com.falkenapps.macgyver.search.fragments.SearchSlidePageFragment;
import com.falkenapps.macgyver.search.interfaces.NavigateButtonClick;

public class SearchActivity extends AppCompatActivity  implements JobSearchFragment.OnFragmentInteractionListener,
        LocationSearchFragment.OnFragmentInteractionListener,
        GuardSearchFragment.OnFragmentInteractionListener,
        QuestionSearchFragment.OnFragmentInteractionListener,
        NavigateButtonClick{


    private SearchPageFragmentAdapter searchPageFragmentAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewPager = (ViewPager) findViewById(R.id.searchViewpager);
        searchPageFragmentAdapter = new SearchPageFragmentAdapter(getSupportFragmentManager(),this,viewPager);
        if (viewPager != null){
            viewPager.setAdapter(searchPageFragmentAdapter);
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }
        Log.d("DDD","On Create SearchActivity");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void nextButtonClicked(int currentPosition) {
        int nextPosition = currentPosition +1;
        if (nextPosition < viewPager.getAdapter().getCount()) {
            viewPager.setCurrentItem(nextPosition);
        }
    }
    @Override
    public void prevButtonClicked(int currentPosition) {
        int nextPosition = currentPosition - 1;
        if (nextPosition >= 0) {
            viewPager.setCurrentItem(nextPosition);
        }

    }

    @Override
    public void saveButtonClicked() {
        /*Search search = new Search("Cerrajero");
        search.setRadius(20.0);
        FullGeoLocation fullGeoLocation = new FullGeoLocation("28080", 40.41808376, -3.68789826);
        search.setFullGeoLocation(fullGeoLocation);
        PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
        pageInteractor.changeFilter(search);*/
        /*for (int i = 0;  i < searchPageFragmentAdapter.getCount(); i++){
            Fragment fragment = searchPageFragmentAdapter.getItem(i);
            if (fragment instanceof LocationSearchFragment){
                ((LocationSearchFragment) fragment).stopGeoLocation();
            }
        }*/
        Helper.hideKeyboard(this);
        setResult(Constant.NEW_SEARCH_SAVE);
        finish();
    }
}
