package com.falkenapps.macgyver.main;

import android.util.Log;

import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.common.UserSession;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.di.LoginComponent;

/**
 * Created by FalkenApps:falken on 4/6/17.
 */

public class MainPresenterImpl implements MainPresenter {
    LoginInteractor loginInteractor;


    MainInteractor mainInteractor;



    @Override
    public User getSignedUser() {
        return loginInteractor.getSignedUser();

    }

    MainView mainView;

    public MainPresenterImpl(MainView mainView,LoginInteractor loginInteractor,MainInteractor mainInteractor){
        this.mainView = mainView;
        this.loginInteractor = loginInteractor;
        this.mainInteractor = mainInteractor;

    }


    @Override
    public void signOff() {
        loginInteractor.doSignOut();

    }


    @Override
    public void writeToLogSessionInfo(UserSession userSession) {
        mainInteractor.registerSession(userSession);

    }

    @Override
    public void writeToLogSearch(Search search) {
        mainInteractor.registerSearch(search);
    }


}
