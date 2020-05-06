package com.falkenapps.macgyver.welcome.di;

import com.falkenapps.macgyver.welcome.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by FalkenApps:falken on 4/4/17.
 */
@Singleton
@Component( modules = {WelcomeModule.class})
public interface WelcomeComponent {
    void inject(WelcomeActivity welcomeActivity);
}
