package com.falkenapps.macgyver.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.falkenapps.macgyver.LocationActivity;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.main.MainActivity;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.login.LoginActivity;
import com.falkenapps.macgyver.main.fragments.PageInteractor;
import com.falkenapps.macgyver.main.fragments.PageInteractorImpl;
import com.falkenapps.macgyver.search.SearchActivity;
import com.falkenapps.macgyver.welcome.di.DaggerWelcomeComponent;
import com.falkenapps.macgyver.welcome.di.WelcomeComponent;
import com.falkenapps.macgyver.welcome.di.WelcomeModule;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements  WelcomeView{

    private static final String ACTIVITY_TAG = Constant.TAG + ":WelcomeActivity";
    private CountDownTimer countDownTimer;

    @BindView(R.id.btnSignIn)
    Button getGetBtnSignIn;
    @BindView(R.id.btnSkipSignIn)
    Button getBtnSignIn;
    @BindString(R.string.welcome_dialog_signInValidation)
    String msgSignInValidation;

    @BindView(R.id.progressBarWelcome)
    ProgressBar progressBar;

    @Inject
    public WelcomePresenter welcomePresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        WelcomeComponent welcomeComponent = DaggerWelcomeComponent.builder().welcomeModule(new WelcomeModule(this)).build();
        welcomeComponent.inject(this);
        getBtnSignIn.setVisibility(View.GONE);
        getGetBtnSignIn.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);




        showDialogSignInValidationSimulation();
        welcomePresenter.isUserSignedIn();



    }

    @OnClick(R.id.btnSignIn)
    public void handleSignIn(){
        Log.d(ACTIVITY_TAG,"handleSignIn");
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();



    }

    @OnClick(R.id.btnSkipSignIn)
    public void handleSkipSignIn(){
        Log.d(ACTIVITY_TAG,"handleSkipSignIn");
        Intent intent = new Intent(this,AdviseSignInActivity.class);
        startActivity(intent);
        finish();


    }


    @Override
    public void hideLoginProcess() {
        getBtnSignIn.setVisibility(View.GONE);
        getGetBtnSignIn.setVisibility(View.GONE);
        welcomePresenter.loadSignedUserInfo();




    }

    @Override
    public void showLoginProcess() {
        progressBar.setVisibility(View.GONE);
        countDownTimer.cancel();
        getBtnSignIn.setVisibility(View.VISIBLE);
        getGetBtnSignIn.setVisibility(View.VISIBLE);



    }

    private void showDialogSignInValidationSimulation(){


        progressBar.setVisibility(View.VISIBLE);

        int timeInMs = Helper.randomInt(Constant.TIME_WELCOME_DIALOG_LOWER,Constant.TIME_WELCOME_DIALOG_UPPER);

        countDownTimer = new CountDownTimer(timeInMs,timeInMs)
        {

            @Override
            public void onTick(long l) {


            }

            @Override
            public void onFinish()
            {
                progressBar.setVisibility(View.GONE);
                goToMainActivity();


            }
        }.start();

    }

    private void goToMainActivity(){
        /*SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
        int locationID = settings.getInt(Constant.LOCATION_TYPE_ID,-1);
        if (locationID != -1) {
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivityForResult(new Intent(this, SearchActivity.class),Constant.RC_NEW_SEARCH);

        }*/
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RC_NEW_SEARCH){

            startActivity(new Intent(this, MainActivity.class));

        }
    }*/

    @Override
    public void onPause(){
        super.onPause();
        welcomePresenter.onStop();

    }

    @Override
    public void onResume(){
        super.onResume();
        welcomePresenter.onStart();
        welcomePresenter.isUserSignedIn();

    }

    @Override
    public void onStart() {
        super.onStart();
        welcomePresenter.onStart();



    }

    @Override
    public void onStop() {
        super.onStop();
        welcomePresenter.onStop();

    }

    @OnClick(R.id.btnGeoFire)
    public void handleGeoFire(){
        startActivity(new Intent(this, LocationActivity.class));
        finish();
    }



}
