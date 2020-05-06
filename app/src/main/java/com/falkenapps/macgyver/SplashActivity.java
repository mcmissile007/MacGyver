package com.falkenapps.macgyver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.main.fragments.PageInteractorImpl;
import com.falkenapps.macgyver.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
        String locationAddress = settings.getString(Constant.LOCATION_ADDRESS,"");
        String locationCodePostal = settings.getString(Constant.LOCATION_CODE_POSTAL,Constant.NULL_POSTAL_CODE);
        String locationType = settings.getString(Constant.LOCATION_TYPE, Constant.LOCATION_TYPE_UNSELECT);
        int radius = settings.getInt(Constant.RADIUS_SETTING,Constant.RADIUS_SEARCH);
        int jobCategoryID = settings.getInt(Constant.JOB_CATEGORY_ID,-1);
        String jobCategoryName = settings.getString(Constant.JOB_CATEGORY_NAME,"");
        String question = settings.getString(Constant.QUESTION,"");
        boolean isGuard = settings.getBoolean(Constant.GUARD,false);
        boolean  isHoliday = settings.getBoolean(Constant.HOLIDAY,false);
        Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
        Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

        if (!jobCategoryName.equals("") &&  !locationType.equals(Constant.LOCATION_TYPE_UNSELECT)) {
            Search search = new Search(jobCategoryName);
            search.setRadius(radius);
            if (!locationType.equals(Constant.LOCATION_TYPE_OTHER)) {
                locationCodePostal = Constant.NULL_POSTAL_CODE;
            }
            FullGeoLocation fullGeoLocation = new FullGeoLocation(locationCodePostal, latitude, longitude);
            fullGeoLocation.setAddress(locationAddress);
            search.setFullGeoLocation(fullGeoLocation);
            search.setGuard(isGuard);


            PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
            pageInteractor.startSearch(search);
        }
        //delay main activity at least 5 second
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start your activity here
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }

        }, Helper.randomInt(Constant.MIN_TIME_SPLASH,Constant.MAX_TIME_SPLASH));
    }
}
