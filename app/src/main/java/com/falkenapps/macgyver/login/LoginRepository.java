package com.falkenapps.macgyver.login;

import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public interface LoginRepository {

    void signInWithEmail(String email, String password, SingleObserver signInObserver);
    void signInWithGoogle(final String userIdToken, final SingleObserver signInObserver);
    void signInWithFacebook(final String userIdToken, final SingleObserver signInObserver);
    void signUpWithEmail(final String email,final  String password, final String UserName, SingleObserver singleObserver);
    boolean isUserSignedIn();
    String getUserName();
    String getUserEmail();
    String getUserPhotoURL();
    void signOut();
    void sendRememberPassword(final String email,SingleObserver singleObserver);
    void onStart();
    void onStop();

}
