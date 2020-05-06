package com.falkenapps.macgyver.favorites;

import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorImpl;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by FalkenApps:falken on 7/6/17.
 */

public class FavoritesPresenterImpl implements FavoritesPresenter {
    private FavoritesView favoritesView;
    private FavoritesInteractor favoritesInteractor;
    private LoginInteractor loginInteractor;

    public FavoritesPresenterImpl(FavoritesView favoritesView){
        this.favoritesView = favoritesView;
        this.favoritesInteractor = new FavoritesInteractorImpl();
        this.loginInteractor = new LoginInteractorImpl(new LoginRepositoryImpl());
    }

    @Override
    public void getMyFavorites() {
        if (!loginInteractor.isUserSignedIn()){
            return;
        }
        User user = loginInteractor.getSignedUser();
        if (user == null){
            return;
        }

        Observer<Professional> observer = new Observer<Professional>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Professional professional) {

                if (favoritesView.getProfessionalListSize() == 0){
                    favoritesView.hideDialog();

                }
                favoritesView.addProfessional(professional);




            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        };

        favoritesInteractor.getMyFavorites(Helper.MD5_Hash(user.getEmail()),observer);
        favoritesView.showDialog();

    }

    @Override
    public void deleteFavorite(final String professionalKey) {
        if (!loginInteractor.isUserSignedIn()){
            return;
        }
        User user = loginInteractor.getSignedUser();
        if (user == null){
            return;
        }

        SingleObserver<String> singleObserver = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {
                if (value.equals(Constant.OK)){
                    //first delete from RecyclerView, not necessary know if the operation was succeed

                }

            }

            @Override
            public void onError(Throwable e) {

            }
        };

        favoritesInteractor.deleteFavorite(Helper.MD5_Hash(user.getEmail()),professionalKey,singleObserver);

    }
}
