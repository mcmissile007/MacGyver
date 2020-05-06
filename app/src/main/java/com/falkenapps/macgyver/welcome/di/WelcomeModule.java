package com.falkenapps.macgyver.welcome.di;

import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorMockImpl;
import com.falkenapps.macgyver.login.LoginRepository;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;
import com.falkenapps.macgyver.welcome.WelcomePresenter;
import com.falkenapps.macgyver.welcome.WelcomePresenterImpl;
import com.falkenapps.macgyver.welcome.WelcomeView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FalkenApps:falken on 4/4/17.
 */

@Module
public class WelcomeModule {
    private WelcomeView welcomeView;

    public WelcomeModule(WelcomeView welcomeView){
        this.welcomeView = welcomeView;
    }

    @Provides
    @Singleton
    WelcomePresenter providesWelcomePresenter(WelcomeView welcomeView, LoginInteractor loginInteractor){
        return new WelcomePresenterImpl(welcomeView,loginInteractor);
    }

    @Provides
    @Singleton
    WelcomeView  providesWelcomeView(){
        return this.welcomeView;
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository loginRepository){
        return new LoginInteractorMockImpl(loginRepository);
    }

    @Provides
    @Singleton
    LoginRepository provideLoginRepository() {
        return new LoginRepositoryImpl();
    }

}
