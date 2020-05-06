package com.falkenapps.macgyver.main.di;

import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorMockImpl;
import com.falkenapps.macgyver.login.LoginRepository;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;
import com.falkenapps.macgyver.main.MainInteractor;
import com.falkenapps.macgyver.main.MainInteractorImpl;
import com.falkenapps.macgyver.main.MainPresenter;
import com.falkenapps.macgyver.main.MainPresenterImpl;
import com.falkenapps.macgyver.main.MainRepository;
import com.falkenapps.macgyver.main.MainRepositoryImpl;
import com.falkenapps.macgyver.main.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by FalkenApps:falken on 4/6/17.
 */
@Module
public class MainModule {
    private MainView mainView;
    public MainModule(MainView mainView){
        this.mainView = mainView;
    }
    
    @Provides
    @Singleton
    MainPresenter providesMainPresenter(MainView mainView, LoginInteractor loginInteractor, MainInteractor mainInteractor){
        return new MainPresenterImpl(mainView,loginInteractor,mainInteractor);
    }

    @Provides
    @Singleton
    MainView  providesMainView(){
        return this.mainView;
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository loginRepository){
        return new LoginInteractorMockImpl(loginRepository);
    }

    @Provides
    @Singleton
    MainInteractor providesMainInteractor(MainRepository mainRepository){
        return new MainInteractorImpl(mainRepository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository() {
        return new LoginRepositoryImpl();
    }

    @Provides
    @Singleton
    MainRepository provideMainRepository() {
        return new MainRepositoryImpl();
    }


}
