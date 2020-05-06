package com.falkenapps.macgyver.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.login.di.DaggerSignUpWithEmailComponent;
import com.falkenapps.macgyver.login.di.SignUpWithEmailComponent;
import com.falkenapps.macgyver.login.di.SignUpWithEmailModule;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpWithEmailActivity extends AppCompatActivity implements SignUpWithEmailView{

    private static final String ACTIVITY_TAG = Constant.TAG + ":SignUpWithEmail";

    @BindString(R.string.signUpWithEmail_title)
    String title;

    @BindString(R.string.login_message_invalidEmail)
    String msgInvalidEmail;

    @BindString(R.string.login_message_emptyPassword)
    String msgEmptyPassword;

    @BindString(R.string.login_message_emptyUserName)
    String msgEmptyUserName;

    @BindString(R.string.login_message_emptyEmail)
    String msgEmptyEmail;

    @BindString(R.string.login_message_minPasswordLength)
    String msgMinPasswordLength;

    @BindString(R.string.login_message_rememberPasswordQuestion)
    String msgRememberPasswordQuestion;

    @BindString(R.string.yes)
    String msgYes;

    @BindString(R.string.no)
    String msgNo;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.editTxtEmail)
    EditText inputEmail;

    @BindView(R.id.editTxtPassword)
    EditText inputPassword;

    @BindView(R.id.editTxtUserName)
    EditText inputUserName;

    @BindView(R.id.wrapperPassword)
    TextInputLayout wrapperPassword;

    @BindView(R.id.wrapperUserName)
    TextInputLayout wrapperUserName;


    @BindView(R.id.wrapperEmail)
    TextInputLayout wrapperEmail;

    @BindView(R.id.progressBarSignUp)
    ProgressBar progressBar;





    @Inject
    public SignUpWithEmailPresenter signUpWithEmailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ButterKnife.bind(this);
        SignUpWithEmailComponent signUpWithEmailComponent = DaggerSignUpWithEmailComponent.builder().signUpWithEmailModule(new SignUpWithEmailModule(this)).build();
        signUpWithEmailComponent.inject(this);

        setTitle(title);
    }

    @OnClick(R.id.fabSave)
    public void handleSaveClick(){

        Log.d(ACTIVITY_TAG,"handleSaveClick");



        Helper.hideKeyboard(this);
        progressBar.setVisibility(View.VISIBLE);



        wrapperEmail.setError(null);
        wrapperPassword.setError(null);
        wrapperUserName.setError(null);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String userName = inputUserName.getText().toString();

        if (validateSignUpInputs(email,password,userName)){
            signUpWithEmailPresenter.signUpWithEmail(email,password,userName);
            //showRememberPasswordDialog(email);

        }else{
            progressBar.setVisibility(View.GONE);
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Helper.hideKeyboard(this);
            setResult(Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_CANCELED, new Intent());
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSignUpError() {
        Log.d(ACTIVITY_TAG,"SignUpError");
        progressBar.setVisibility(View.GONE);
        setResult(Constant.SIGN_UP_WITH_EMAIL_ERROR, new Intent());
        finish();
    }

    @Override
    public void showSignUpSuccess() {
        Log.d(ACTIVITY_TAG,"SignUpSuccess");
        progressBar.setVisibility(View.GONE);
        setResult(Constant.SIGN_UP_WITH_EMAIL_OK, new Intent());
        finish();

    }

    @Override
    public void showEmailAlreadyExists() {
        Log.d(ACTIVITY_TAG,"showEmailAlreadyExists");
        progressBar.setVisibility(View.GONE);
        String email = inputEmail.getText().toString();
        if (email != null){
            showRememberPasswordDialog(email);

        }



    }

    @Override
    public void showErrorSendingRememberPasswordEmail() {
        Log.d(ACTIVITY_TAG,"showErrorSendingRememberPasswordEmail");
        progressBar.setVisibility(View.GONE);
        setResult(Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_SENT_REMEMBER_PASSWORD_EMAIL, new Intent());
        finish();
    }

    @Override
    public void showOKSentRememberPasswordEmail() {
        Log.d(ACTIVITY_TAG,"showOKSentRememberPasswordEmail");
        progressBar.setVisibility(View.GONE);
        setResult(Constant.SIGN_UP_WITH_EMAIL_INCOMPLETE_SENT_REMEMBER_PASSWORD_EMAIL, new Intent());
        finish();

    }

    private void showRememberPasswordDialog(final String email){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(msgRememberPasswordQuestion,email));
        builder.setCancelable(true);

        builder.setPositiveButton(
                msgYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        signUpWithEmailPresenter.sendRememberPassword(email);
                        dialog.cancel();


                    }
                });

        builder.setNegativeButton(
                msgNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }

    private boolean validateSignUpInputs(String email,String password,String userName){


        if (userName.isEmpty()) {
            inputUserName.requestFocus();
            wrapperUserName.setError(msgEmptyUserName);
            return false;
        }


        if (email.isEmpty()) {
            inputEmail.requestFocus();
            wrapperEmail.setError(msgEmptyEmail);
            return false;
        }


        if (!Helper.isValidEmail(email)){
            //Toast.makeText(this,invalidEmail, Toast.LENGTH_SHORT).show();
            wrapperEmail.setError(msgInvalidEmail);
            inputEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            inputPassword.requestFocus();
            wrapperPassword.setError(msgEmptyPassword);
            return false;
        }



        if (password.length() < Constant.MIN_PASSWORD_LENGTH){
            String message = String.format(msgMinPasswordLength,Constant.MIN_PASSWORD_LENGTH);
            inputPassword.requestFocus();
            wrapperPassword.setError(message);
            return false;
        }




        return true;


    }
}
