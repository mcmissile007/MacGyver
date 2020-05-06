package com.falkenapps.macgyver.professional;

import com.falkenapps.macgyver.common.Comment;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.favorites.FavoritesInteractor;


import com.falkenapps.macgyver.favorites.FavoritesInteractorImpl;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorImpl;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by FalkenApps:falken on 7/4/17.
 */

public class ProfessionalPresenterImpl implements ProfessionalPresenter {
    private ProfessionalView professionalView;
    private ProfessionalInteractor professionalInteractor;

    @Override
    public void stop() {
        professionalInteractor.stop();
    }

    @Override
    public void start() {
        professionalInteractor.start();

    }

    private Observer addCommentObserver;
    private Observer updateCommentListObserver;


    public ProfessionalPresenterImpl(ProfessionalView professionalView){
        this.professionalView = professionalView;
        this.professionalInteractor = new ProfessionalInteractorImpl();
    }
    @Override
    public void getComments(final String professionalKey) {
        if (addCommentObserver == null){
            addCommentObserver = new Observer<Comment>(){
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Comment comment) {
                    professionalView.addComment(comment);
                    professionalView.hideDialog();


                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

            professionalInteractor.getComments(professionalKey,addCommentObserver);
            professionalView.showDialog();

        }



    }

    @Override
    public void getCommentList(String professionalKey) {


        if (updateCommentListObserver == null) {
            updateCommentListObserver = new Observer<List<Comment>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<Comment> commentList) {
                    professionalView.setCommentList(commentList);

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            professionalInteractor.getComments(professionalKey, updateCommentListObserver);
        }
    }

    @Override
    public void addProfessionalToFavorites(String professionalKey) {

        LoginInteractor loginInteractor = new LoginInteractorImpl(new LoginRepositoryImpl());
        if (!loginInteractor.isUserSignedIn()){
            professionalView.showMessageUserIsNotSigned();
            return;
        }

        if (professionalKey == null){
            professionalView.showAddToFavoritesWithError();
            return;

        }

        User user = loginInteractor.getSignedUser();


        SingleObserver<String> singleObserver = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {

                if (value.equals(Constant.OK)){
                    professionalView.showAddToFavoritesSucceed();

                }else{
                    professionalView.showAddToFavoritesWithError();
                }

            }

            @Override
            public void onError(Throwable e) {
                professionalView.showAddToFavoritesWithError();

            }
        };

        professionalInteractor.addProfessionalToFavorites(Helper.MD5_Hash(user.getEmail()),professionalKey,singleObserver);

    }

    @Override
    public void deleteProfessionalFromFavorites(String professionalKey) {

        LoginInteractor loginInteractor = new LoginInteractorImpl(new LoginRepositoryImpl());
        if (!loginInteractor.isUserSignedIn()){
            professionalView.showMessageUserIsNotSigned();
            return;
        }

        if (professionalKey == null){
            professionalView.showAddToFavoritesWithError();
            return;

        }

        User user = loginInteractor.getSignedUser();


        SingleObserver<String> singleObserver = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String value) {

                if (value.equals(Constant.OK)){
                    professionalView.showDeleteFromFavoritesSucceed();

                }else{
                    professionalView.showDeleteFromFavoritesWithError();
                }

            }

            @Override
            public void onError(Throwable e) {
                professionalView.showDeleteFromFavoritesWithError();

            }
        };

        professionalInteractor.deleteProfessionalFromFavorites(Helper.MD5_Hash(user.getEmail()),professionalKey,singleObserver);

    }

    @Override
    public void isProfessionalFavorite(final String professionalKey) {
        LoginInteractor loginInteractor = new LoginInteractorImpl(new LoginRepositoryImpl());
        FavoritesInteractor favoritesInteractor = new FavoritesInteractorImpl();


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
                if (professional.getKey().equals(professionalKey)){
                    professionalView.showDeleteFromFavorites();


                }


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        favoritesInteractor.getMyFavorites(Helper.MD5_Hash(user.getEmail()),observer);
    }
}
