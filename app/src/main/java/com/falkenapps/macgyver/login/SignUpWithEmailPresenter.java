package com.falkenapps.macgyver.login;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public interface SignUpWithEmailPresenter {
    void signUpWithEmail(final String email,  final String password, final String userName);
    void sendRememberPassword(final String email);
    void onStart();
    void onStop();
}
