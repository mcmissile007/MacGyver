package com.falkenapps.macgyver.main.fragments;

import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.Search;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public interface PageInteractor {

    void changeFilter(Search search);
    void notifyMeAddProfessional(Observer observer);
    void notifyMeRemoveProfessional(Observer observer);
    void notifyMeUpdateProfessional(Observer observer);
    void notifyMeChangeFilter(Observer observer);
     void removeMeFromAddObservers(Observer observer);
    void removeMeFromUpdateObservers(Observer observer);
    void removeMeFromRemoveObservers(Observer observer);
    void removeMeFromChangeFilterObservers(Observer observer);
    void removeMeFromCurrentProfesionalListObservers(Observer observer);
    void addMeFromCurrentProfesionalListObservers(Observer observer);
    void getCurrentProfessionalsList();
    void startSearch(Search search);


}
