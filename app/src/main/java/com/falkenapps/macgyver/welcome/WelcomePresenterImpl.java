package com.falkenapps.macgyver.welcome;

import android.util.Log;

import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.login.LoginInteractor;

/**
 * Created by FalkenApps:falken on 4/4/17.
 */

public class WelcomePresenterImpl implements WelcomePresenter {

    private WelcomeView welcomeView;
    private LoginInteractor loginInteractor;

    public WelcomePresenterImpl(WelcomeView welcomeView,LoginInteractor loginInteractor){

        this.welcomeView = welcomeView;
        this.loginInteractor = loginInteractor;

    }
    @Override
    public void isUserSignedIn() {
       // welcomeView.showLoginProcess();
        if (loginInteractor.isUserSignedIn()){
            welcomeView.hideLoginProcess();
        }
        else{
            welcomeView.showLoginProcess();
        }


    }

    @Override
    public void loadSignedUserInfo() {
        loginInteractor.getSignedUser();
    }

    @Override
    public void onStart() {

        loginInteractor.onStart();

    }

    @Override
    public void onStop() {
        loginInteractor.onStop();

    }
}
