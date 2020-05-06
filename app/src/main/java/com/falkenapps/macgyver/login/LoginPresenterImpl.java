package com.falkenapps.macgyver.login;


import android.util.Log;

import com.falkenapps.macgyver.common.Constant;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static com.falkenapps.macgyver.common.Constant.TAG;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public class LoginPresenterImpl  implements  LoginPresenter{
    private LoginView loginView;
    private LoginInteractor loginInteractor;


//private SingleObserver<String> singleObserver;



    @Inject //decorator not required
    public LoginPresenterImpl(LoginView loginView,LoginInteractor loginInteractor){
        this.loginView = loginView;

        this.loginInteractor = loginInteractor;

        /*
        this.singleObserver = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
            }
        };*/




    }
    @Override
    public void validateLoginWithGoogle(final String userIdToken) {
        SingleObserver<String> response = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
                if (value.equals(Constant.OK)){
                    loginView.showLoginSuccess();

                }
                else{
                    loginView.showLoginError();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                loginView.showLoginError();
            }
        };
        loginView.showLoginInProgress();
        loginInteractor.doSignInWithGoogle(userIdToken,response);

    }

    @Override
    public void validateLoginWithFacebook(final String userIdToken) {
        SingleObserver<String> response = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
                if (value.equals(Constant.OK)){
                    loginView.showLoginSuccess();

                }
                else{
                    loginView.showLoginError();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                loginView.showLoginError();
            }
        };
        loginView.showLoginInProgress();
        loginInteractor.doSignInWithFacebook(userIdToken,response);

    }



    @Override
    public void validateLoginWithEmail(String email, String password) {

        SingleObserver<String> response = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
                if (value.equals(Constant.OK)){
                    loginView.showLoginSuccess();

                }
                else if (value.equals(Constant.EMAIL_NOT_VERIFIED)){
                    loginView.showEmailNotVerified();
                }
                else if (value.equals(Constant.WRONG_PASSWORD)){
                    loginView.showWrongPassword();
                }
                else if (value.equals(Constant.EMAIL_NOT_EXISTS)){
                    loginView.showEmailNotExists();
                }
                else{
                    loginView.showLoginError();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                loginView.showLoginError();
            }
        };
        loginView.showLoginInProgress();
        loginInteractor.doSignInWithEmail(email,password,response);


    }

    @Override
    public void sendRememberPassword(final String email) {
        SingleObserver<String> response = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
                if (value.equals(Constant.OK)){
                    Log.d(TAG, "OK");
                    loginView.showOKSentRememberPasswordEmail();

                }
                else{
                    Log.d(TAG, "ERROR");
                    loginView.showErrorSendingRememberPasswordEmail();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                Log.d(TAG, "ERROR");
                loginView.showErrorSendingRememberPasswordEmail();

            }
        };

        loginInteractor.sendRememberPassword(email,response);

    }

    @Override
    public boolean isUserSignedIn() {
        return loginInteractor.isUserSignedIn();
    }

    @Override
    public void loadSignedUserInfo() {
        loginInteractor.getSignedUser();
    }

    @Override
    public void onStart() {

        loginInteractor.onStart();

    }

    @Override
    public void onStop() {
        loginInteractor.onStop();

    }


}
