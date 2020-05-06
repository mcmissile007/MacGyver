package com.falkenapps.macgyver.login;


import com.falkenapps.macgyver.common.User;

import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public interface LoginInteractor {
    void doSignInWithEmail(String email, String password, SingleObserver signInObserver);
    void doSignInWithGoogle(String userIdToken, SingleObserver signInObserver);
    void doSignInWithFacebook(final String userIdToken, SingleObserver signInObserver);
    void doSignUpWithEmail(final String email, final String password, final String userName, SingleObserver singleObserver);
    void sendRememberPassword(final String email,SingleObserver singleObserver);
    void doSignOut();
    boolean isUserSignedIn();
    User getSignedUser();
    void onStart();
    void onStop();



}
