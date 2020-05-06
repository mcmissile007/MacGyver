package com.falkenapps.macgyver.welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.falkenapps.macgyver.main.MainActivity;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.login.LoginActivity;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdviseSignInActivity extends AppCompatActivity {

    @BindString(R.string.yes)
    String msgYes;

    @BindString(R.string.no)
    String msgNo;

    @BindString(R.string.doYouWantFinishApp)
    String msgDoYouWantFinishApp;


    private static final String ACTIVITY_TAG = Constant.TAG + ":AdviseSignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise_sign_in);
        ButterKnife.bind(this);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("");*/
    }




    @OnClick(R.id.btnContinue)
    public void handleContinue(){
        Log.d(ACTIVITY_TAG,"handleSignUp");
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @OnClick(R.id.btnSignUp)
    public void handleSignUp(){
        Log.d(ACTIVITY_TAG,"handleContinue");
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(msgDoYouWantFinishApp)
                .setCancelable(false)
                .setPositiveButton(msgYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdviseSignInActivity.this.finish();
                    }
                })
                .setNegativeButton(msgNo, null)
                .show();
    }
}
