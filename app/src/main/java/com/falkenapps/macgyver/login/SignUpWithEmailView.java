package com.falkenapps.macgyver.login;

/**
 * Created by FalkenApps:falken on 3/28/17.
 */

public interface SignUpWithEmailView {

    void  showSignUpError();
    void  showSignUpSuccess();
    void  showEmailAlreadyExists();
    void showErrorSendingRememberPasswordEmail();
    void showOKSentRememberPasswordEmail();

}
