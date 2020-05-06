package com.falkenapps.macgyver.login;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public interface LoginView {

     void  showLoginError();
     void  showLoginSuccess();
     void  showLoginInProgress();
     void  showEmailNotVerified();
     void  showWrongPassword();
     void  showEmailNotExists();
    void showErrorSendingRememberPasswordEmail();
    void showOKSentRememberPasswordEmail();


}
