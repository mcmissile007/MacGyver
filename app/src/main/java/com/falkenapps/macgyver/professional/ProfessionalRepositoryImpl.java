package com.falkenapps.macgyver.professional;

import com.falkenapps.macgyver.common.Comment;
import com.falkenapps.macgyver.common.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

class ProfessionalRepositoryImpl implements ProfessionalRepository {

    List<Comment> commentList;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private Observer addObserver;

    public ProfessionalRepositoryImpl(){
        this.commentList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    String commentKey = snapshot.getKey();
                    if (commentKey != null) {
                        Comment comment = snapshot.getValue(Comment.class);
                        if (comment != null) {
                            commentList.add(comment);
                            if (addObserver != null) {
                                Observable.just(comment).subscribe(addObserver);
                            }

                        }
                    }


                }


                /*
                String commentKey = dataSnapshot.getValue(String.class);
                if (commentKey != null) {
                    Comment comment = dataSnapshot.child(commentKey).getValue(Comment.class);
                    if (comment != null) {
                        commentList.add(comment);
                        if (addObserver != null) {
                            Observable.just(comment).subscribe(addObserver);
                        }

                    }
                }*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void getCommentList(String professionalKey, Observer observer) {
        if (commentList != null && commentList.size() > 0) {
            Observable.just(commentList).subscribe(observer);
        }

    }


    public void addProfessionalToFavorites_old(String userKey, String professionalKey, final SingleObserver singleObserver) {
        databaseReference.child(Constant.FAVORITES_DATA_REFERENCE).child(userKey).push().setValue(professionalKey, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    Single.just(Constant.ERROR).subscribe(singleObserver);



                }else{
                    Single.just(Constant.OK).subscribe(singleObserver);

                }

            }
        });

    }

    @Override
    public void addProfessionalToFavorites(String userKey, String professionalKey, final SingleObserver singleObserver) {
        databaseReference.child(Constant.FAVORITES_DATA_REFERENCE).child(userKey).child(professionalKey).setValue(true, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    Single.just(Constant.ERROR).subscribe(singleObserver);



                }else{
                    Single.just(Constant.OK).subscribe(singleObserver);

                }

            }
        });

    }

    @Override
    public void deleteProfessionalFromFavorites(String userKey, String professionalKey, final SingleObserver singleObserver) {
        databaseReference.child(Constant.FAVORITES_DATA_REFERENCE).child(userKey).child(professionalKey).removeValue( new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    Single.just(Constant.ERROR).subscribe(singleObserver);



                }else{
                    Single.just(Constant.OK).subscribe(singleObserver);

                }

            }
        });


    }

    @Override
    public void stop() {
        FirebaseDatabase.getInstance().goOffline();


    }

    @Override
    public void start() {
        FirebaseDatabase.getInstance().goOnline();
    }

    @Override
    public void getComments(String professionalKey, Observer observer) {
        //for DEMO
        professionalKey = "demoProfessional";
        this.addObserver = observer;
        databaseReference.child(Constant.COMMENTS_DATA_REFERENCE).child(professionalKey).addValueEventListener(valueEventListener);


    }
}
