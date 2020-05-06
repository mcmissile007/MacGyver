package com.falkenapps.macgyver.professional;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

public interface ProfessionalInteractor {
    void getComments(String professionalKey, Observer observer);
    void getCommentList(String professionalKey, Observer observer);
    void addProfessionalToFavorites(String userKey, String professionalKey, SingleObserver singleObserver);
    void deleteProfessionalFromFavorites(String userKey, String professionalKey, SingleObserver singleObserver);
    void stop();
    void start();
}
