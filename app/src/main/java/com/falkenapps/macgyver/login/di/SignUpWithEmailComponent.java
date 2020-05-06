package com.falkenapps.macgyver.login.di;

import com.falkenapps.macgyver.login.SignUpWithEmailActivity;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by FalkenApps:falken on 3/2/17.
 */

@Singleton
@Component(modules = {SignUpWithEmailModule.class})
public interface SignUpWithEmailComponent {
    void inject(SignUpWithEmailActivity signUpWithEmailActivity);


}
