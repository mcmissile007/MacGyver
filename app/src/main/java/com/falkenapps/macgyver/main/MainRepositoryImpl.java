package com.falkenapps.macgyver.main;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.common.UserSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by FalkenApps:falken on 5/30/17.
 */

public class MainRepositoryImpl implements  MainRepository {

    private DatabaseReference databaseReference;

    public MainRepositoryImpl(){
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void registerSession(UserSession userSession) {
        //off in developing
        //databaseReference.child(Constant.LOGIN_LOG_DATA_REFERENCE).child(userSession.getKey()).setValue(userSession);

    }

    @Override
    public void registerSearch(Search search) {
        String searchKey = search.getJob() + "_" + String.valueOf(search.getDate().getTime());
        //off in developing
        //databaseReference.child(Constant.SEARCH_LOG_DATA_REFERENCE).child(searchKey).setValue(search);

    }
}
