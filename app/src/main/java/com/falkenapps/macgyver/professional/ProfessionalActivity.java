package com.falkenapps.macgyver.professional;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Comment;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.Haversine;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Professional;
import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.login.LoginInteractor;
import com.falkenapps.macgyver.login.LoginInteractorImpl;
import com.falkenapps.macgyver.login.LoginRepositoryImpl;
import com.falkenapps.macgyver.professional.adapters.CommentRVAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by FalkenApps:falken on 6/27/17.
 */

public class ProfessionalActivity extends AppCompatActivity  implements  ProfessionalView{

    Professional professional;



    ActionBar actionBar;
    private static final String ACTIVITY_TAG = Constant.TAG + ":ProfessionalActivity";
    private CommentRVAdapter adapter;
    private  RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Handler closeDialogHandler;


    private ProfessionalPresenter professionalPresenter;


    @BindView(R.id.txtProfActivity)
    TextView txtProfActivity;


    @BindView(R.id.txtProfDistance)
    TextView txtProfDistance;


    @BindView(R.id.txtProfPrice)
    TextView txtProfPrice;

    @BindView(R.id.txtProfTitle)
    TextView txtProfTitle;

    @BindView(R.id.txtProfLocation)
    TextView txtProfLocation;


    @BindView(R.id.imgProfessional)
    ImageView imgProfessional;

    @BindView(R.id.imgProfScore)
    ImageView imgProfScore;

    @BindView(R.id.imgProfGuard)
    ImageView imgProfGuard;

    @BindView(R.id.btnActionAddToFavorites)
    com.github.clans.fab.FloatingActionButton btnAddToFavorites;

    @BindView(R.id.btnActionDeleteFromFavorites)
    com.github.clans.fab.FloatingActionButton btnDeleteFromFavorites;

    @BindString(R.string.professional_addToFavorites_OK)
    String addToFavoritesOK;

    @BindString(R.string.professional_addToFavorites_ERROR)
    String addToFavoritesERROR;

    @BindString(R.string.professional_chat_introduction)
    String chatIntroduction;


    @BindString(R.string.professional_addToFavorites_mustBeSigned)
    String mustBeSigned;

    @BindString(R.string.professional_addToFavorites_whereToSign)
    String whereToSign;


    @OnClick(R.id.btnActionCall)
    public void phoneCall(){
        /*Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constant.FALKEN_NUMBER));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            startActivity(intent);
        }*/

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.FALKEN_NUMBER));
        startActivity(intent);



        }

    @OnClick(R.id.btnActionChat)
    public void chat(){
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

            SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
            String question= settings.getString(Constant.QUESTION,"");

            String introduction = "";
            LoginInteractor loginInteractor = new LoginInteractorImpl(new LoginRepositoryImpl());
            if (loginInteractor.isUserSignedIn()){
                User user = loginInteractor.getSignedUser();
                if (user != null){
                    introduction = String.format(chatIntroduction,user.getName());
                }
            }

            if (introduction.equals("") && question.equals("")){
                Uri uri = Uri.parse("smsto:" + Constant.FALKEN_NUMBER);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

            }
            else {
                String message = "";

                if (!introduction.equals("") && !question.equals("")) {
                    message = introduction + " . " + question;
                }

                if (introduction.equals("") && !question.equals("")) {
                    message = question;
                }

                if (!introduction.equals("") && question.equals("")) {
                    message = introduction;
                }

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.putExtra("jid", Constant.FALKEN_NUMBER + "@s.whatsapp.net"); //phone number without "+" prefix
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }


        }
        else{
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_LONG).show();
            startActivity(goToMarket);
        }
    }



    @OnClick(R.id.btnActionAddToFavorites)
    public void AddToFavoritesHandle(){

        Log.d(ACTIVITY_TAG,"Add to favorites clicked");
        professionalPresenter.addProfessionalToFavorites(professional.getKey());
    }

    @OnClick(R.id.btnActionDeleteFromFavorites)
    public void deleteFromFavorites(){
        Log.d(ACTIVITY_TAG,"Delete from  clicked");
        professionalPresenter.deleteProfessionalFromFavorites(professional.getKey());
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constant.DELETED,true);
        setResult(Constant.RC_PROFESSIONAL,resultIntent);
        finish();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_old);
        setContentView(R.layout.activity_professional);
        ButterKnife.bind(this);

        professionalPresenter = new ProfessionalPresenterImpl(this);

        professionalPresenter.start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfessional);




        setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:

        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);


        }

        recyclerView = (RecyclerView) findViewById(R.id.profRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //adapter = new CommentRVAdapter(Helper.getRandomCommentList(),this );

        adapter = new CommentRVAdapter(new ArrayList<Comment>(),this );

        recyclerView.setAdapter(adapter);









        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("professionalObject");
        }
        professional = new Gson().fromJson(jsonMyObject, Professional.class);

        if (professional != null){
            Log.d(ACTIVITY_TAG, professional.toString());
            actionBar.setTitle(professional.getName());
            txtProfActivity.setText(professional.getActivity());
            txtProfTitle.setText(professional.getTitle());

            //professionalViewHolder.score.setText(String.format(Locale.getDefault(), "%.1f", professional.getScore()));

            double score = professional.getScore();

            Drawable myDrawable;

            if (score >= 4.5){

                myDrawable = ContextCompat.getDrawable(this,R.drawable.scorefive);


            }else if(score < 4.5 && score >=3.5){
                myDrawable = ContextCompat.getDrawable(this,R.drawable.scorefour);

            }else if(score < 3.5 && score >=2.5){
                myDrawable = ContextCompat.getDrawable(this,R.drawable.scorethree);

            } else if(score < 2.5 && score >=1.5){
                myDrawable = ContextCompat.getDrawable(this,R.drawable.scoretwo);

            }else if(score < 1.5 && score >=0) {
                myDrawable = ContextCompat.getDrawable(this,R.drawable.scoreone);

            }else{
                myDrawable = ContextCompat.getDrawable(this,R.drawable.scorethree);

            }

            if (myDrawable != null){
                imgProfScore.setImageDrawable(myDrawable);
            }

            if (!professional.isGuard()){
                imgProfGuard.setVisibility(View.INVISIBLE);

            }



            txtProfLocation.setText(professional.getSmallBusinessAddress() +  " " + professional.getCity());



            txtProfPrice.setText(String.format(Locale.getDefault(), "%.0f " + getResources().getString(R.string.main_pricePerHour) , (professional.getPricePerHour()+professional.getDeparturePrice())));
            //professionalViewHolder.price.setText(String.format(Locale.getDefault(), "%.1f", (professional.getPricePerHour())));

            SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
            Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
            Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

            if (latitude == 0){
                latitude =  Constant.GEO_LOCATION.getLatitude();
            }
            if (longitude == 0){
                longitude =  Constant.GEO_LOCATION.getLongitude();
            }

            double distance =  (Haversine.distance(professional.getFullGeoLocation().getLatitude(),
                    professional.getFullGeoLocation().getLongitude(),latitude
                    ,longitude)
            );
            //double distance =0;

            txtProfDistance.setText(String.format(Locale.getDefault(), "%.1f Km", distance));

            imgProfessional.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.com_facebook_profile_picture_blank_square));

            if (!professional.getPhotoURL().equals("")) {

                Glide.with(this).load(professional.getPhotoURL()).into(imgProfessional);

            }




            professionalPresenter.isProfessionalFavorite(professional.getKey());
            professionalPresenter.getComments(professional.getKey());

            //necessary to start scroll on top not in the middle of the activity
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewProf);
            scrollView.setFocusableInTouchMode(true);
            scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);


        }
    }

    @Override
    public void showDeleteFromFavorites() {
        btnAddToFavorites.setVisibility(View.GONE);
        btnDeleteFromFavorites.setVisibility(View.VISIBLE);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void addComment(Comment comment) {

        adapter.addComment(comment);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setCommentList(List<Comment> commentList) {
        adapter.setCommentList(commentList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPause(){
        super.onPause();
        professionalPresenter.stop();

    }

    @Override
    public void onStop(){
        super.onStop();

    }


    @Override
    public void onResume(){
        super.onResume();
        professionalPresenter.start();

    }


    @Override
    public void showDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.professional_searching_comments));
        progressDialog.setCancelable(false);
        progressDialog.show();
        closeDialogHandler = new Handler();
        closeDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }




            }
        }, Constant.MAX_TIME_LOADING_COMMENTS);

    }


    @Override
    public void hideDialog(){
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void showMessageUserIsNotSigned() {
        //Toast.makeText(this,mustBeSigned  , Toast.LENGTH_LONG).show();
        //Toast.makeText(this,whereToSign  , Toast.LENGTH_LONG).show();

    }

    @Override
    public void showAddToFavoritesSucceed() {
        //Toast.makeText(this, addToFavoritesOK, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showAddToFavoritesWithError() {
        //Toast.makeText(this, addToFavoritesERROR, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showDeleteFromFavoritesSucceed() {

    }

    @Override
    public void showDeleteFromFavoritesWithError() {

    }


}
