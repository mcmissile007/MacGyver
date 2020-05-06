package com.falkenapps.macgyver.login.di;

import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorMockImpl;
import com.falkenapps.macgyver.login.LoginPresenter;
import com.falkenapps.macgyver.login.LoginPresenterImpl;
import com.falkenapps.macgyver.login.LoginRepository;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;
import com.falkenapps.macgyver.login.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FalkenApps:falken on 3/2/17.
 */

@Module
public class LoginModule {

    private LoginView loginView;

    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(LoginView loginView,LoginInteractor loginInteractor){
        return new LoginPresenterImpl(loginView,loginInteractor);
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return this.loginView;
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
