package com.falkenapps.macgyver.favorites;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 7/6/17.
 */

public interface FavoritesInteractor {
    void getMyFavorites(String userKey,Observer observer);
    void deleteFavorite(String userKey,String professionalKey,SingleObserver singleObserver);
}
