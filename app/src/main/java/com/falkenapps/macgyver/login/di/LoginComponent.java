package com.falkenapps.macgyver.login.di;

import com.falkenapps.macgyver.login.LoginActivity;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by FalkenApps:falken on 3/2/17.
 */

@Singleton
@Component(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
    LoginRepository getRepository();
    LoginInteractor getInteractor();

}
