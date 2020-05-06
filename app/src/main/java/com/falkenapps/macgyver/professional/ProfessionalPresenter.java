package com.falkenapps.macgyver.professional;

import io.reactivex.Observer;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

public interface ProfessionalPresenter {
    void getComments(String professionalKey);
    void getCommentList(String professionalKey);
    void addProfessionalToFavorites(String professionalKey);
    void deleteProfessionalFromFavorites(String professionalKey);
    void isProfessionalFavorite(String professionalKey);
    void stop();
    void start();
}
