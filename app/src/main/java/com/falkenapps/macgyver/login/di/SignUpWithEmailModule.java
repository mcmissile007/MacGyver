package com.falkenapps.macgyver.login.di;

import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorMockImpl;
import com.falkenapps.macgyver.login.LoginRepository;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;
import com.falkenapps.macgyver.login.SignUpWithEmailPresenter;
import com.falkenapps.macgyver.login.SignUpWithEmailPresenterImpl;
import com.falkenapps.macgyver.login.SignUpWithEmailView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FalkenApps:falken on 3/2/17.
 */

@Module
public class SignUpWithEmailModule {

    private SignUpWithEmailView signUpWithEmailView;

    public SignUpWithEmailModule(SignUpWithEmailView signUpWithEmailView) {
        this.signUpWithEmailView = signUpWithEmailView;
    }

    @Provides
    @Singleton
    SignUpWithEmailPresenter providesSignUpWithEmailPresenter(SignUpWithEmailView signUpWithEmailView, LoginInteractor loginInteractor){
        return new SignUpWithEmailPresenterImpl(signUpWithEmailView,loginInteractor);
    }

    @Provides
    @Singleton
    SignUpWithEmailView providesSignUpWithEmailView(){
        return this.signUpWithEmailView;
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
