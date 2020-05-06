package com.falkenapps.macgyver.main.fragments;

import android.content.SharedPreferences;
import android.util.Log;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.falkenapps.macgyver.common.Constant.TAG;

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public class PagePresenterImpl implements  PagePresenter{
    private PageFragment pageFragment;
    private PageInteractor pageInteractor;
    private List<Professional> professionals;
    private Search search;
    private Observer addObserver;
    private Observer updateObserver;
    private Observer removeObserver;
    private Observer changeFilterObserver;
    private Observer<List<Professional>> currentProfessionalListObserver;


    public PagePresenterImpl(PageFragment pageFragment){
        this.pageFragment = pageFragment;
        this.pageInteractor = PageInteractorImpl.getInstance();
        this.professionals = new ArrayList<>();
        /*this.search = new Search("Fontanero");
        search.setFullGeoLocation(Constant.GEO_LOCATION);*/
        SharedPreferences settings = pageFragment.getContext().getSharedPreferences(Constant.PREFS_SEARCH, 0);
        String lastAddress = settings.getString(Constant.LOCATION_ADDRESS,"");
        String locationCodePostal = settings.getString(Constant.LOCATION_CODE_POSTAL,"");
        String locationType = settings.getString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_UNSELECT);
        int jobCategoryID = settings.getInt(Constant.JOB_CATEGORY_ID,-1);
        String jobCategoryName = settings.getString(Constant.JOB_CATEGORY_NAME,"");
        String question = settings.getString(Constant.QUESTION,"");
        boolean isGuard = settings.getBoolean(Constant.GUARD,false);
        boolean  isHoliday = settings.getBoolean(Constant.HOLIDAY,false);
        Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
        Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

        Log.d(TAG,"PagePresenterImpl");
        Log.d(TAG,"Job category id:" + jobCategoryID);
        Log.d(TAG,"Job category name:" + jobCategoryName);
        Log.d(TAG,"Location type:" + locationType);
        Log.d(TAG,"LocationCodePostal:" + locationCodePostal);
        Log.d(TAG,"LastAddress:" + lastAddress);
        Log.d(TAG,"Latitude:" + latitude.toString());
        Log.d(TAG,"Longitude:" + longitude.toString());
        Log.d(TAG,"isGuard:" + isGuard);
        Log.d(TAG,"isHoliday:" + isHoliday);
        Log.d(TAG,"Question:" + question);

        Search search = new Search(jobCategoryName);
        search.setRadius(Constant.RADIUS_SEARCH);
        FullGeoLocation fullGeoLocation = new FullGeoLocation(Constant.NULL_POSTAL_CODE, latitude, longitude);
        search.setFullGeoLocation(fullGeoLocation);
        search.setGuard(isGuard);

        Log.d(TAG,"startSearch");
        pageInteractor.startSearch(search);


        Log.d("DDD","MY_LOCATION:" + search.getFullGeoLocation().toString());


    }




    @Override
    public void getProfessionals() {

        //pageFragment.removeAllProfessionals();

        if (currentProfessionalListObserver == null) {
            currentProfessionalListObserver = new Observer<List<Professional>>() {


                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, " onSubscribe addObserverList : " + d.isDisposed());

                }

                @Override
                public void onNext(List<Professional> professionalList) {
                    Log.d("JB_DEBUG", " onNext professionalList : ");

                    pageFragment.setProfessionalList(professionalList);

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, " onError professionalList : " + e.getMessage());

                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete professionalList");

                }
            };
            pageInteractor.addMeFromCurrentProfesionalListObservers(currentProfessionalListObserver);
            pageInteractor.getCurrentProfessionalsList();
        }
        else{
            pageInteractor.getCurrentProfessionalsList();
        }



        if (addObserver == null) {
            addObserver = new Observer<Professional>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, " onSubscribe addObserver : " + d.isDisposed());

                }

                @Override
                public void onNext(Professional professional) {
                    Log.d("JB_DEBUG", " onNext professional addObserver: " + professional.toString());
                    pageFragment.addProfessional(professional);

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, " onError addObserver : " + e.getMessage());


                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete addObserver");


                }
            };

            pageInteractor.notifyMeAddProfessional(addObserver);
        }

        if (updateObserver == null) {

            updateObserver = new Observer<Professional>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, " onSubscribe updateObserver : " + d.isDisposed());

                }

                @Override
                public void onNext(Professional professional) {
                    Log.d(TAG, " onNext professional updateObserver: " + professional.toString());
                    pageFragment.updateProfessional(professional);

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, " onError updateObserver : " + e.getMessage());


                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete updateObserver");


                }
            };

            pageInteractor.notifyMeUpdateProfessional(updateObserver);
        }



        if (removeObserver == null) {
            removeObserver = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, " onSubscribe removeObserver : " + d.isDisposed());

                }

                @Override
                public void onNext(String s) {
                    Log.d(TAG, " onNext professional removeObserver: " + s);
                    pageFragment.removeProfessional(s);

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, " onError removeObserver : " + e.getMessage());

                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete removeObserver");

                }
            };

            pageInteractor.notifyMeRemoveProfessional(removeObserver);
        }



        if (changeFilterObserver == null) {
            changeFilterObserver = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, " onSubscribe changeFilterObserver : " + d.isDisposed());

                }

                @Override
                public void onNext(String s) {
                    Log.d(TAG, " onNext professional changeFilterObserver: " + s);
                    if (s.equals(Constant.OK)){
                        pageFragment.removeAllProfessionals();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, " onError changeFilterObserver : " + e.getMessage());

                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete changeFilterObserver");

                }
            };
            pageInteractor.notifyMeChangeFilter(changeFilterObserver);
        }




    }

    @Override
    public void onStop() {
        pageInteractor.removeMeFromAddObservers(addObserver);
        pageInteractor.removeMeFromUpdateObservers(updateObserver);
        pageInteractor.removeMeFromRemoveObservers(removeObserver);
        pageInteractor.removeMeFromChangeFilterObservers(changeFilterObserver);
        pageInteractor.removeMeFromCurrentProfesionalListObservers(currentProfessionalListObserver);
        addObserver = null;
        updateObserver = null;
        removeObserver = null;
        changeFilterObserver = null;
        currentProfessionalListObserver = null;

    }
}
