package com.falkenapps.macgyver.main.fragments;

import android.content.SharedPreferences;
import android.util.Log;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.Search;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;


/*
he difference should be clearer when you look at the behaviour of each when you pass it an Iterable (for example a List):

Observable.just(someList) will give you 1 emission - a List.

Observable.from(someList) will give you N emissions - each item in the list.

The ability to pass multiple values to just is a convenience feature; the following are functionally the same:

Observable.just(1, 2, 3);
Observable.from(1, 2, 3);
 */

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public class PageRepositoryImpl implements  PageRepository {

    List<Professional> professionals;
    List<Professional> allProfessionalsInZone;
    List<String> keys;
    private DatabaseReference databaseReference;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    private ValueEventListener valueEventListener;
    private GeoQueryEventListener geoQueryEventListener;
    private Search search;
    private Set<Observer> addObserverSet;
    private Set<Observer> updateObserverSet;
    private Set<Observer> removeObserverSet;
    private Set<Observer> changeFilterObserverSet;
    private Set<Observer> currentProfessionalsListObserverSet;




    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;

    }

    private PageRepositoryImpl(){

        professionals = new ArrayList<>();
        allProfessionalsInZone = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        geoFire = new GeoFire(databaseReference.child(Constant.GEOFIRE_DATA_REFERENCE));

        /*search = new Search(Constant.DEFAULT_JOB);
        search.setRadius(Constant.RADIUS_SEARCH);
        search.setFullGeoLocation(Constant.GEO_LOCATION);*/

        addObserverSet = new HashSet<>();
        updateObserverSet = new HashSet<>();
        removeObserverSet = new HashSet<>();
        changeFilterObserverSet = new HashSet<>();
        currentProfessionalsListObserverSet = new HashSet<>();

        valueEventListener = new ValueEventListener() {  //use with child(profID)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Professional professional = dataSnapshot.getValue(Professional.class);
                Log.d(Constant.TAG, "onDataChange function:");
                if (professional != null) {
                    Log.d(Constant.TAG, "onDataChange.:" + professional.toString());
                    double distance =  (Haversine.distance(professional.getFullGeoLocation().getLatitude(),
                            professional.getFullGeoLocation().getLongitude(),search.getFullGeoLocation().getLatitude()
                            ,search.getFullGeoLocation().getLongitude())
                    );
                    professional.setDistance(distance);
                    if (!allProfessionalsInZone.contains(professional)) {
                        allProfessionalsInZone.add(professional);
                    }

                    //filter by search

                    if(search.isGuard()){
                        if (!professional.isGuard()){
                            return;
                        }
                    }

                    if (professional.getJobs().containsKey(search.getJob())){

                        if (!professionals.contains(professional)) {


                            if (professionals.size() <= Constant.LIMIT_SEARCH_RESULTS) {
                                Log.d(Constant.TAG, "AddProfesional:" + professional.toString());
                                professionals.add(professional);
                                for (Observer observer : addObserverSet) {
                                    Observable.just(professional).subscribe(observer);
                                }
                            }

                        } else {
                            Log.d(Constant.TAG, "UpdateProfesional:" + professional.toString());
                            int position = professionals.indexOf(professional);
                            professionals.set(position, professional);

                            int positionAll = allProfessionalsInZone.indexOf(professional);
                            allProfessionalsInZone.set(positionAll, professional);

                            //professionals.remove(professional);
                            //professionals.add(professional);
                            for (Observer observer : updateObserverSet) {
                                Observable.just(professional).subscribe(observer);
                            }

                        }
                    }
                    else{
                        Log.d(Constant.TAG,"no job in list");
                    }
                    //Log.d(Constant.TAG, "SendListToOrder:" + professional.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constant.TAG,databaseError.getMessage());

            }
        };
        geoQueryEventListener = new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                String message = String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude);
                Log.d(Constant.TAG, message);
                databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(key).addValueEventListener(valueEventListener);

            }

            @Override
            public void onKeyExited(String key) {
                String message = String.format("Key %s is no longer in the search area", key);
                Log.d(Constant.TAG, message);
                databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(key).removeEventListener(valueEventListener);
                Professional deleteProfessional = new Professional();
                deleteProfessional.setKey(key);
                professionals.remove(deleteProfessional);
                allProfessionalsInZone.remove(deleteProfessional);
                for (Observer observer: removeObserverSet){
                    Observable.just(key).subscribe(observer);
                }
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                String message = String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude);
                Log.d(Constant.TAG, message);

            }

            @Override
            public void onGeoQueryReady() {
                String message = "All initial data has been loaded and events have been fired!.";
                Log.d(Constant.TAG, message);
                Log.d("DDD",message);

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                String message = "There was an error with this query: " + error;
                Log.d(Constant.TAG, message);

            }
        };

        /*geoQuery = geoFire.queryAtLocation(new GeoLocation(search.getFullGeoLocation().getLatitude()
                ,search.getFullGeoLocation().getLongitude())
                ,search.getRadius());
        geoQuery.addGeoQueryEventListener(geoQueryEventListener);*/

        Log.d("DDD","Impl PageRepository");


        }

    private static class PageRepositoryImplHelper{
        private static  final PageRepositoryImpl INSTANCE = new PageRepositoryImpl();
    }

    public static PageRepositoryImpl getInstance(){
        return PageRepositoryImplHelper.INSTANCE;
    }




    @Override
    public void changeFilter(Search search) {
        Log.d("JB_FILTER","changeFilter");

        professionals = new ArrayList<>();

        if (geoQuery != null) {
            Log.d("JB_FILTER","geoQuery not null");

            if (hasChangedGeoSearch(this.search,search)) {

                Log.d("JB_FILTER","hasChangedGeoSearch YES");

                allProfessionalsInZone = new ArrayList<>();

                geoQuery.removeAllListeners();
                for (Observer observer : changeFilterObserverSet) {
                    Observable.just(Constant.OK).subscribe(observer);
                }
                geoQuery = geoFire.queryAtLocation(new GeoLocation(search.getFullGeoLocation().getLatitude()
                                , search.getFullGeoLocation().getLongitude())
                        , (double)search.getRadius());

                geoQuery.addGeoQueryEventListener(geoQueryEventListener);
                Log.d("DDD", "CHANGED FILTER");
            }
            else{

                Log.d("JB_FILTER","hasChangedGeoSearch NO");
                for (Professional professional: allProfessionalsInZone){
                    if(search.isGuard()){
                        if (!professional.isGuard()){
                            continue;
                        }
                    }

                    if (professional.getJobs().containsKey(search.getJob())) {
                        if (!professionals.contains(professional)) {
                            if (professionals.size() <= Constant.LIMIT_SEARCH_RESULTS) {
                                Log.d(Constant.TAG, "AddProfesional:" + professional.toString());
                                professionals.add(professional);
                                for (Observer observer : addObserverSet) {
                                    Observable.just(professional).subscribe(observer);
                                }
                            }

                        }

                    }

                }
          }

        }else{
            startSearch(search);
        }
        this.setSearch(search);


    }

    private boolean hasChangedGeoSearch(Search s1, Search s2){

        if (s1.getFullGeoLocation().getLongitude() != s2.getFullGeoLocation().getLongitude()){
            return true;
        }
        if (s1.getFullGeoLocation().getLatitude() != s2.getFullGeoLocation().getLatitude()){
            return true;
        }
        if (s1.getRadius() != s2.getRadius()){
            return true;
        }
        return false;
    }

    @Override
    public void startSearch(Search search) {

        if (geoQuery == null) {
            this.setSearch(search);
            professionals = new ArrayList<>();
            allProfessionalsInZone = new ArrayList<>();
            geoQuery = geoFire.queryAtLocation(new GeoLocation(search.getFullGeoLocation().getLatitude()
                            , search.getFullGeoLocation().getLongitude())
                    , (double)search.getRadius());
            geoQuery.addGeoQueryEventListener(geoQueryEventListener);
            Log.d("DDD", "Started SEARCH");
        }
        else{
            Log.d("DDD", "Already started search");
        }

    }

    @Override
    public void notifyMeAddProfessional(Observer observer) {
        addObserverSet.add(observer);
    }

    @Override
    public void notifyMeRemoveProfessional(Observer observer) {
        removeObserverSet.add(observer);

    }

    @Override
    public void notifyMeUpdateProfessional(Observer observer) {
        updateObserverSet.add(observer);

    }

    @Override
    public void notifyMeChangeFilter(Observer observer) {
        changeFilterObserverSet.add(observer);

    }

    @Override
    public void removeMeFromAddObservers(Observer observer) {
        addObserverSet.remove(observer);
        Log.d(Constant.TAG,"removeMeFromAddObservers");

    }

    @Override
    public void removeMeFromUpdateObservers(Observer observer) {
        updateObserverSet.remove(observer);
        Log.d(Constant.TAG,"removeMeFromUpdateObservers");

    }

    @Override
    public void removeMeFromRemoveObservers(Observer observer) {
        removeObserverSet.remove(observer);
        Log.d(Constant.TAG,"removeMeFromRemoveObservers");

    }

    @Override
    public void removeMeFromChangeFilterObservers(Observer observer) {
        changeFilterObserverSet.remove(observer);
        Log.d(Constant.TAG,"removeMeFromChangeFilterObservers");

    }

    @Override
    public void removeMeFromCurrentProfesionalListObservers(Observer observer) {
        currentProfessionalsListObserverSet.remove(observer);
        Log.d(Constant.TAG,"removeMeFromCurrentProfesionalListObservers");

    }

    @Override
    public void addMeFromCurrentProfesionalListObservers(Observer observer) {
        currentProfessionalsListObserverSet.add(observer);

    }

    @Override
    public void getCurrentProfessionalsList() {

        for (Observer observer: currentProfessionalsListObserverSet){
            Log.d("JB_DEBUG_REFE","getCurrentProfessionalsList:" + Integer.toHexString(System.identityHashCode(professionals)));
            Log.d("JB_DEBUG_REFE","getCurrentProfessionalsList:" + observer.toString());
            Observable.just(professionals).subscribe(observer);
        }





    }
}
