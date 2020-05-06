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

public class SignUpWithEmailPresenterImpl implements  SignUpWithEmailPresenter{
    private SignUpWithEmailView signUpWithEmailView;
    private LoginInteractor loginInteractor;

    @Inject //decorator not required
    public SignUpWithEmailPresenterImpl(SignUpWithEmailView signUpWithEmailView, LoginInteractor loginInteractor){
        this.signUpWithEmailView = signUpWithEmailView;

        this.loginInteractor = loginInteractor;

    }

    @Override
    public void signUpWithEmail(String email, String password, String userName) {
        SingleObserver<String> response = new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {

                Log.d(TAG, " onNext value : " + value);
                if (value.equals(Constant.OK)){
                    signUpWithEmailView.showSignUpSuccess();

                }
                if (value.equals(Constant.EMAIL_ALREADY_EXISTS)){
                    signUpWithEmailView.showEmailAlreadyExists();

                }
                else{
                    signUpWithEmailView.showSignUpError();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                signUpWithEmailView.showSignUpError();
            }
        };
        loginInteractor.doSignUpWithEmail(email,password,userName,response);

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
                    signUpWithEmailView.showOKSentRememberPasswordEmail();

                }
                else{
                    Log.d(TAG, "ERROR");
                    signUpWithEmailView.showErrorSendingRememberPasswordEmail();

                }
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
                Log.d(TAG, "ERROR");
                signUpWithEmailView.showErrorSendingRememberPasswordEmail();

            }
        };

        loginInteractor.sendRememberPassword(email,response);

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
