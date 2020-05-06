package com.falkenapps.macgyver.login;


import com.falkenapps.macgyver.common.User;

import javax.inject.Inject;

import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    @Inject //decorator not required
    public LoginInteractorImpl(LoginRepository loginRepository) {

        this.loginRepository = loginRepository;


    }

    @Override
    public void onStart() {
        loginRepository.onStart();


    }

    @Override
    public void onStop() {
        loginRepository.onStop();

    }

    @Override
    public void doSignInWithEmail(String email, String password, SingleObserver signInObserver) {

        loginRepository.signInWithEmail(email, password, signInObserver);


    }

    @Override
    public void doSignInWithGoogle(final String userIdToken, final SingleObserver signInObserver) {
        loginRepository.signInWithGoogle(userIdToken, signInObserver);

    }

    @Override
    public void doSignInWithFacebook(final String userIdToken, SingleObserver signInObserver) {
        loginRepository.signInWithFacebook(userIdToken, signInObserver);

    }

    @Override
    public void doSignUpWithEmail(String email, String password, String userName, SingleObserver singleObserver) {
        loginRepository.signUpWithEmail(email, password, userName, singleObserver);

    }

    @Override
    public void sendRememberPassword(final String email, SingleObserver singleObserver) {
        loginRepository.sendRememberPassword(email, singleObserver);
    }


    @Override
    public void doSignOut() {
        loginRepository.signOut();
    }

    @Override
    public boolean isUserSignedIn() {
        return loginRepository.isUserSignedIn();


    }

    @Override
    public User getSignedUser() {
        User user = User.getInstance();
        user.setEmail(loginRepository.getUserEmail());
        user.setName(loginRepository.getUserName());
        user.setPhotoURL(loginRepository.getUserPhotoURL());
        user.setSigned(loginRepository.isUserSignedIn());
        return  user;

    }


}
