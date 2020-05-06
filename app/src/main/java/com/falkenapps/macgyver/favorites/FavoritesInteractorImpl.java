package com.falkenapps.macgyver.favorites;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/6/17.
 */

public class FavoritesInteractorImpl implements FavoritesInteractor {

    private FavoritesRepository favoritesRepository;

    public FavoritesInteractorImpl(){
        this.favoritesRepository = new FavoritesRepositoryImpl();
    }

    @Override
    public void getMyFavorites(String userKey, Observer observer) {
        favoritesRepository.getMyFavorites(userKey,observer);

    }

    @Override
    public void deleteFavorite(String userKey, String professionalKey, SingleObserver singleObserver) {
        favoritesRepository.deleteFavorite(userKey,professionalKey,singleObserver);

    }
}
