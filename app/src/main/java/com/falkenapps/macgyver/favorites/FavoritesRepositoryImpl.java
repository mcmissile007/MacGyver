package com.falkenapps.macgyver.favorites;

import android.renderscript.Sampler;
import android.util.Log;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.common.Professional;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/6/17.
 */

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private List<Professional> professionalList;
    private List<String> favoriteKeyList;
    private DatabaseReference databaseReference;
    private ValueEventListener professionalValueEventListener;
    private ValueEventListener favoriteKeysValueEventListener;
    private Observer addObserver;



    public FavoritesRepositoryImpl(){
        this.professionalList = new ArrayList<>();
        this.favoriteKeyList = new ArrayList<>();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.professionalValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Professional professional = dataSnapshot.getValue(Professional.class);
                Log.d(Constant.TAG, "onDataChange function:");
                if (professional != null) {
                    professional.setDistance(0);
                    if (!professionalList.contains(professional)) {
                        professionalList.add(professional);
                        Observable.just(professional).subscribe(addObserver);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        this.favoriteKeysValueEventListener = new ValueEventListener() {
            /*@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("JB_DEBUG","onDataChange");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String favoriteKey = snapshot.getKey();
                    if (favoriteKey != null){
                        String professionalKey = snapshot.getValue(String.class);
                        if (professionalKey != null) {
                            favoriteKeyList.add(professionalKey);
                            databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(professionalKey).addListenerForSingleValueEvent(professionalValueEventListener);
                        }

                    }

                }

            }*/

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("JB_DEBUG","onDataChange");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String professionalKey = snapshot.getKey();
                    Boolean isFavorite = snapshot.getValue(Boolean.class);

                    if (professionalKey != null ) {
                        if (isFavorite) {
                            favoriteKeyList.add(professionalKey);
                            databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(professionalKey).addListenerForSingleValueEvent(professionalValueEventListener);
                        }

                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("JB_DEBUG","onCancelled");

            }
        };

    }





    @Override
    public void getMyFavorites(String userKey, Observer observer) {
        this.addObserver = observer;
        this.favoriteKeyList = new ArrayList<>();
        this.professionalList = new ArrayList<>();
        databaseReference.child(Constant.FAVORITES_DATA_REFERENCE).child(userKey).addValueEventListener(favoriteKeysValueEventListener);

    }

    @Override
    public void deleteFavorite(String userKey, String professionalKey, SingleObserver singleObserver) {

    }
}
