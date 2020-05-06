package com.falkenapps.macgyver.main;

import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.common.UserSession;

/**
 * Created by FalkenApps:falken on 5/30/17.
 */

public class MainInteractorImpl implements  MainInteractor {

    private MainRepository mainRepository;


    public MainInteractorImpl(MainRepository loginRepository) {

        this.mainRepository = loginRepository;

    }


    @Override
    public void registerSession(UserSession session) {
        mainRepository.registerSession(session);

    }

    @Override
    public void registerSearch(Search search) {
        mainRepository.registerSearch(search);
    }
}
