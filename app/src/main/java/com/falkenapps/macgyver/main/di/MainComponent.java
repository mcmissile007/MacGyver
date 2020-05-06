package com.falkenapps.macgyver.main.di;

import com.falkenapps.macgyver.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by FalkenApps:falken on 4/6/17.
 */
@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
