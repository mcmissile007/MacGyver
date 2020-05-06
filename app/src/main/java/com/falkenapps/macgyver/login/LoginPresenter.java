package com.falkenapps.macgyver.login;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public interface LoginPresenter {
    void validateLoginWithEmail(String email, String password);
    void validateLoginWithGoogle(final String userIdToken);
    void validateLoginWithFacebook(final String userIdToken);
    void sendRememberPassword(final String email);
    boolean isUserSignedIn();
    void loadSignedUserInfo();
    void onStart();
    void onStop();
}
