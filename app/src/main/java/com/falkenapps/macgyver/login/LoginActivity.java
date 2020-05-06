package com.falkenapps.macgyver.login;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.falkenapps.macgyver.main.MainActivity;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.login.di.DaggerLoginComponent;
import com.falkenapps.macgyver.login.di.LoginComponent;
import com.falkenapps.macgyver.login.di.LoginModule;
import com.falkenapps.macgyver.search.SearchActivity;
import com.falkenapps.macgyver.welcome.WelcomeActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.falkenapps.macgyver.common.Constant.RC_NEW_SEARCH;
import static com.falkenapps.macgyver.common.Constant.RC_SIGN_UP_WITH_EMAIL;

public class LoginActivity extends AppCompatActivity implements LoginView,GoogleApiClient.OnConnectionFailedListener {



    @BindString(R.string.login_message_invalidEmail)
    String invalidEmail;

    @BindString(R.string.login_message_minPasswordLength)
    String minPasswordLength;


    @BindView(R.id.editTxtEmail)
    EditText inputEmail;

    @BindView(R.id.editTxtPassword)
    EditText inputPassword;



    @BindView(R.id.wrapperPassword)
    TextInputLayout wrapperPassword;


    @BindView(R.id.wrapperEmail)
    TextInputLayout wrapperEmail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btnSignInWithFacebook)
    LoginButton btnSignInWithFacebook;

    @BindView(R.id.btnSignInWithGoogle)
    SignInButton btnSignInWithGoogle;

    @BindView(R.id.btnSignInWithEmail)
    Button btnSignInWithEmail;

    @BindView(R.id.btnSignUpWithEmail)
    Button btnSignUpWithEmail;


    @BindString(R.string.login_button_signInGoogle)
    String login_button_signInGoogle;

    @BindString(R.string.login_title)
    String loginTitle;

    @BindString(R.string.login_message_success)
    String loginSuccess;

    @BindString(R.string.login_message_unSuccess)
    String loginUnSuccess;

    @BindString(R.string.doYouWantFinishApp)
    String msgDoYouWantFinishApp;


    @BindString(R.string.login_message_emptyEmail)
    String emptyEmail;

    @BindString(R.string.login_message_verifyEmail)
    String msgVerifyEmail;

    @BindString(R.string.login_message_rememberPasswordEmailSent)
    String msgRememberPasswordEmailSent;

    @BindString(R.string.login_message_errorSendingRememberPasswordEmail)
    String msgErrorSendingRememberPasswordEmail;

    @BindString(R.string.login_button_signUpError)
    String msgSingUpError;




    @BindString(R.string.login_message_emptyPassword)
    String emptyPassword;

    @BindString(R.string.login_message_mustVerifyEmail)
    String msgMustVerifyEmail;

    @BindString(R.string.login_message_emailNotExist)
    String msgEmailNotExists;

    @BindString(R.string.login_message_wrongPassword)
    String msgWrongPassword;

    @BindString(R.string.login_message_adviceEmail)
    String msgAdviceEmail;

    @BindString(R.string.login_message_advicePassword)
    String msgAdvicePassword;

    @BindString(R.string.login_message_tooManyAttempts)
    String msgTooManyAttempts;


    @BindString(R.string.yes)
    String msgYes;

    @BindString(R.string.no)
    String msgNo;


    private static final String ACTIVITY_TAG = Constant.TAG + ":LoginActivity";
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private CallbackManager callbackManager;
    private static int numberLoginAttempts = 0;



    @Inject
    public LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoginComponent loginComponent = DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build();
        loginComponent.inject(this);
        configSignInWithGoogle();
        configGoogleButton();

        callbackManager  = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut(); //necessary because if fireBase is logOut Facebook LoginButton still appears wit "exit" in the next Register
        //configSigInWithEmailButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/
        setTitle(loginTitle);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void configSigInWithEmailButton(){
        //btnSignInWithEmail.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));




    }

    private void configSigUpWithEmailButton(){



    }

    private void configGoogleButton(){
        btnSignInWithGoogle.setStyle(SignInButton.SIZE_WIDE,SignInButton.COLOR_DARK);
        for (int i = 0; i < btnSignInWithGoogle.getChildCount(); i++) {
            View v = btnSignInWithGoogle.getChildAt(i);

            if (v instanceof TextView) {
                TextView textView = (TextView) v;
                textView.setPadding(0, 0, 20, 0); //align center like Facebook Button
                textView.setText(login_button_signInGoogle);
                return;
            }
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        loginPresenter.onStop();

    }

    @Override
    public void onResume(){
        super.onResume();
        loginPresenter.onStart();
        Helper.hideKeyboard(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.onStart();
        Helper.hideKeyboard(this);


    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.onStop();

    }

    @OnClick(R.id.btnSignUpWithEmail)
    public void handleSignUpWithEmail() {

        /*boolean alreadySignIn = loginPresenter.isUserSignedIn();
        Log.d(ACTIVITY_TAG,"Already SignIn:" + alreadySignIn);*/

        Intent signUpIntent = new Intent(this,SignUpWithEmailActivity.class);
        startActivityForResult(signUpIntent,RC_SIGN_UP_WITH_EMAIL);

        /*
        PackageManager pm = this.getPackageManager();
        if (Helper.isPackageInstalled("com.whatsapp", pm)){


            //opcion 1
            //ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            //ClipData clip = ClipData.newPlainText("MacGyver", "text to send");
            //clipboard.setPrimaryClip(clip);

            //Uri uri = Uri.parse("smsto:" + "34676206731");
            //Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
            //sendIntent.setPackage("com.whatsapp");
            //startActivity(sendIntent);


            //http://stackoverflow.com/questions/19081654/send-text-to-specific-contact-whatsapp
            // esta opcion mejor incluye el texto
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.putExtra("jid", "34676206731" + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);


        }
        else{
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_LONG).show();
            startActivity(goToMarket);
        }

        */





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constant.RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String fullName = acct.getDisplayName();
                String email = acct.getEmail();
                String userId =  acct.getId();
                String userIdToken = acct.getIdToken();
                Log.d(ACTIVITY_TAG,"Google SignIn OK:" + fullName + "->" +  email);
                Log.d(ACTIVITY_TAG,"Google userId:" + userId);
                Log.d(ACTIVITY_TAG,"Google userIdToken:" + userIdToken);
                loginPresenter.validateLoginWithGoogle(userIdToken);

            } else {
                // Google Sign In failed, update UI appropriately
                Log.d(ACTIVITY_TAG,"Google SignIn failed");

            }

            return;

        }
        else if ( requestCode == RC_SIGN_UP_WITH_EMAIL){

            Helper.hideKeyboard(this);

           if (resultCode == Constant.SIGN_UP_WITH_EMAIL_OK){
               Toast.makeText(this,msgVerifyEmail, Toast.LENGTH_LONG).show();


           }
            if (resultCode == Constant.SIGN_UP_WITH_EMAIL_ERROR){
                Toast.makeText(this,msgSingUpError, Toast.LENGTH_LONG).show();


            }

            if (resultCode == Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_CANCELED){

            }

            if (resultCode == Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_SENT_REMEMBER_PASSWORD_EMAIL){

                Toast.makeText(this,msgRememberPasswordEmailSent,Toast.LENGTH_LONG).show();

            }

            if (resultCode == Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_ERROR_SENDING_REMEMBER_PASSWORD_EMAIL){
                Toast.makeText(this,msgErrorSendingRememberPasswordEmail,Toast.LENGTH_LONG).show();
            }








        }
        else {

            if (callbackManager != null) {

                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }

    }




    @OnClick(R.id.btnSignInWithFacebook)
    public void handleSignInWithFacebook(){


        LoginButton loginButton = btnSignInWithFacebook;
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(ACTIVITY_TAG, "facebook:onSuccess:" + loginResult.getAccessToken().toString());
                //handleFacebookAccessToken(loginResult.getAccessToken());
                loginPresenter.validateLoginWithFacebook(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d(ACTIVITY_TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(ACTIVITY_TAG, "facebook:onError", error);

            }
        });



    }

    @OnClick(R.id.btnSignInWithGoogle)
    public void handleSignInWithGoogle(){


        //configSignInWithGoogle(); change to OnCreate
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, Constant.RC_SIGN_IN_GOOGLE);

    }

    public void configSignInWithGoogle(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this) //automatic connection/disconnection from google play services in onStart and OnStop
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.btnSignInWithEmail)
    public void handleSignInWithEmail() {


        wrapperEmail.setError(null);
        wrapperPassword.setError(null);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!validateSignInInputs(email, password)) {
            return;
        }

        Helper.hideKeyboard(this);

        loginPresenter.validateLoginWithEmail(email, password);


    }

    @Override
    public void showLoginError() {

        Log.d(ACTIVITY_TAG, "LoginError");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,loginUnSuccess, Toast.LENGTH_LONG).show();

    }


    @Override
    public void showWrongPassword() {
        Log.d(ACTIVITY_TAG,"showWrongPassword");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,msgWrongPassword, Toast.LENGTH_LONG).show();
        inputPassword.requestFocus();
        if (numberLoginAttempts == 0) {
            Toast.makeText(this, msgAdvicePassword, Toast.LENGTH_LONG).show();
        }
        numberLoginAttempts += 1;

        if (numberLoginAttempts > Constant.MAX_LOGIN_ATTEMPTS){
            String email = (inputEmail.getText().toString() == null) ? "" : inputEmail.getText().toString();
            showRememberPasswordDialog(email);

        }
        

    }

    private void showRememberPasswordDialog(final String email){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(msgTooManyAttempts,email));
        builder.setCancelable(true);

        builder.setPositiveButton(
                msgYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loginPresenter.sendRememberPassword(email);
                        numberLoginAttempts = 0;
                        dialog.cancel();


                    }
                });

        builder.setNegativeButton(
                msgNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        numberLoginAttempts = 1;
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }

    @Override
    public void showErrorSendingRememberPasswordEmail() {
        Log.d(ACTIVITY_TAG,"showErrorSendingRememberPasswordEmail");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,msgErrorSendingRememberPasswordEmail,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showOKSentRememberPasswordEmail() {
        Log.d(ACTIVITY_TAG,"showOKSentRememberPasswordEmail");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,msgRememberPasswordEmailSent,Toast.LENGTH_LONG).show();

    }



    @Override
    public void showEmailNotExists() {
        Log.d(ACTIVITY_TAG,"showEmailNotExists");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,msgEmailNotExists, Toast.LENGTH_LONG).show();
        inputEmail.requestFocus();
        Toast.makeText(this,msgAdviceEmail, Toast.LENGTH_LONG).show();



    }

    @Override
    public void showLoginSuccess() {
        Log.d(ACTIVITY_TAG,"LoginSuccess");
        progressBar.setVisibility(View.GONE);
        //Toast.makeText(this,loginSuccess, Toast.LENGTH_LONG).show();
        loginPresenter.loadSignedUserInfo();
        startActivity(new Intent(this, MainActivity.class));

       /* SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
        int locationID = settings.getInt(Constant.LOCATION_TYPE_ID,-1);
        if (locationID != -1) {
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivityForResult(new Intent(this, SearchActivity.class), RC_NEW_SEARCH);

        }*/

        numberLoginAttempts = 0;
        finish();

    }

    @Override
    public void showLoginInProgress() {
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void showEmailNotVerified() {
        Log.d(ACTIVITY_TAG,"showEmailNotVerified");
        progressBar.setVisibility(View.GONE);
        String email = (inputEmail.getText().toString() == null) ? "" : inputEmail.getText().toString();
        Toast.makeText(this,String.format(msgMustVerifyEmail,email), Toast.LENGTH_LONG).show();
        numberLoginAttempts = 0;



    }


    private boolean validateSignInInputs(String email,String password){
        if (email.isEmpty()) {
            inputEmail.requestFocus();
            wrapperEmail.setError(emptyEmail);
            return false;
        }


        if (!Helper.isValidEmail(email)){
            //Toast.makeText(this,invalidEmail, Toast.LENGTH_LONG).show();
            wrapperEmail.setError(invalidEmail);
            inputEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            inputPassword.requestFocus();
            wrapperPassword.setError(emptyPassword);
            return false;
        }
        return true;

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(ACTIVITY_TAG,"onConnectionFailed with Google Play Services");

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(msgDoYouWantFinishApp)
                .setCancelable(false)
                .setPositiveButton(msgYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finish();
                    }
                })
                .setNegativeButton(msgNo, null)
                .show();
    }


}
