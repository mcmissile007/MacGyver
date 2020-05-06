package com.falkenapps.macgyver.login;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.falkenapps.macgyver.common.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

/**
 * Created by FalkenApps:falken on 2/28/17.
 */

public class LoginRepositoryImpl implements  LoginRepository {


    private static  final String TAG = Constant.TAG + ":LoginRepositoryImpl";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Inject  //decorator not required
    public LoginRepositoryImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener(){


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =   firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG,"onAuthStateChanged: signed_in:" + user.getUid());

                }
                else{
                    Log.d(TAG,"onAuthStateChanged: signed_out:");

                }
            }
        };

    }

    @Override
    public void signInWithEmail(final String email, final String password, final SingleObserver signInObserver) {
        /*Log.d(TAG,"SignIn OK:" + email + ":" + password);
        Single.just("Repository OK").subscribe(signInObserver);*/
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"signInWithEmailAndPassword: onComplete");
                        if (task.isSuccessful()){
                            Log.d(TAG,"signInWithEmailAndPassword: successful: "+ email);

                            if (hashUserEmailVerified()) {
                                Single.just(Constant.OK).subscribe(signInObserver);
                            }
                            else{
                                Single.just(Constant.EMAIL_NOT_VERIFIED).subscribe(signInObserver);

                            }


                        }
                        else{
                            Log.d(TAG,"signInWithEmailAndPassword: unsuccessful");
                            Log.d(TAG,task.getException().toString());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Single.just(Constant.WRONG_PASSWORD).subscribe(signInObserver);

                            }
                            else if (task.getException() instanceof FirebaseAuthInvalidUserException){
                                Single.just(Constant.EMAIL_NOT_EXISTS).subscribe(signInObserver);



                            }
                            else {
                                Single.just(Constant.ERROR).subscribe(signInObserver);
                            }


                        }
                    }
                });





    }

    @Override
    public void signInWithGoogle(final String userIdToken, final SingleObserver signInObserver) {
        AuthCredential credential = GoogleAuthProvider.getCredential(userIdToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()  {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithGoogle:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()){
                            Log.d(TAG,"signInWithGoogle: successful");
                            Single.just(Constant.OK).subscribe(signInObserver);


                        }
                        else{
                            Log.d(TAG,"signInWithGoogle: unsuccessful");
                            Log.w(TAG, "signInWithGoogle", task.getException());
                            Single.just(Constant.ERROR).subscribe(signInObserver);

                        }



                    }
                });
    }

    @Override
    public void signInWithFacebook(final String userIdToken, final SingleObserver signInObserver) {
        AuthCredential credential = FacebookAuthProvider.getCredential(userIdToken);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()  {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithFacebook:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()){
                            Log.d(TAG,"signInWithFacebook: successful");
                            Single.just(Constant.OK).subscribe(signInObserver);


                        }
                        else{
                            Log.d(TAG,"signInWithFacebook: unsuccessful");
                            Log.w(TAG, "signInWithFacebook", task.getException());


                        }



                    }
                });

    }


    @Override
    public void signUpWithEmail(final String email, final String password, final String userName, final SingleObserver singleObserver) {
        /*Log.d(TAG,"SignIn OK:" + email + ":" + password);
        Single.just("Repository OK").subscribe(signInObserver);*/
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete");
                        if (task.isSuccessful()){


                            Log.d(TAG,"createUserWithEmail: successful");
                            Single.just(Constant.OK).subscribe(singleObserver);
                            updateUserName(userName);
                            sendEmailVerification();

                        }
                        else{
                            Log.d(TAG,"createUserWithEmail: unsuccessful");
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                Single.just(Constant.EMAIL_ALREADY_EXISTS).subscribe(singleObserver);

                            }
                            else{

                                Single.just(Constant.ERROR).subscribe(singleObserver);

                            }


                        }
                    }
                });





    }

    private boolean hashUserEmailVerified(){
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        return (user!=null) ? user.isEmailVerified() : false;

    }

    private void updateUserName(final String userName){
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .setPhotoUri(Uri.parse("https://d30y9cdsu7xlg0.cloudfront.net/png/12462-200.png"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

    }

    private void sendEmailVerification(){
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification();

    }

    @Override
    public boolean isUserSignedIn() {

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        if (user != null){
           String provider = user.getProviders().get(0);
            if (provider.equals("password")){
                if (user.isEmailVerified()){
                    return true;
                }

            }else
            {
                return true;
            }

        }
        return false;



    }

    @Override
    public void signOut() {

            firebaseAuth.signOut();


    }



    @Override
    public void sendRememberPassword(final String email,  final SingleObserver singleObserver) {

        Log.d(TAG, "sendRememberPassword");

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()  {
            @Override
            public void onComplete(@NonNull Task<java.lang.Void> task) {
                Log.d(TAG, "sendPasswordResetEmail:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()){
                    Log.d(TAG,"sendPasswordResetEmail: successful");
                    Single.just(Constant.OK).subscribe(singleObserver);
                    Log.d(TAG,"sendPasswordResetEmail: sent");

                }
                else{
                    Log.d(TAG,"sendPasswordResetEmail: unsuccessful");
                    Log.w(TAG, "sendPasswordResetEmail", task.getException());
                    Single.just(Constant.ERROR).subscribe(singleObserver);

                }



            }
        });



    }

    @Override
    public void onStart() {
        if (authStateListener != null) {
            firebaseAuth.addAuthStateListener(authStateListener);

        }
    }

    @Override
    public void onStop() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }

    @Override
    public String getUserName() {
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        return (user!=null) ? user.getDisplayName() : "";
    }

    @Override
    public String getUserEmail() {
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        return (user!=null) ? user.getEmail() : "";
    }

    @Override
    public String getUserPhotoURL() {
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        return (user!=null) ? user.getPhotoUrl().toString(): "";
    }
}
