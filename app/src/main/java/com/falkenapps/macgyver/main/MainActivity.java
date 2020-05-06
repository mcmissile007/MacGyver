package com.falkenapps.macgyver.main;

/*http://www.sgoliver.net/blog/interfaz-de-usuario-en-android-navigation-drawer-navigationview/*/

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Search;
import com.falkenapps.macgyver.common.User;
import com.falkenapps.macgyver.common.UserSession;
import com.falkenapps.macgyver.favorites.FavoritesActivity;
import com.falkenapps.macgyver.info.InfoActivity;
import com.falkenapps.macgyver.login.LoginActivity;
import com.falkenapps.macgyver.main.adapters.PFAdapter;
import com.falkenapps.macgyver.main.di.DaggerMainComponent;
import com.falkenapps.macgyver.main.di.MainComponent;
import com.falkenapps.macgyver.main.di.MainModule;
import com.falkenapps.macgyver.main.fragments.PageInteractorImpl;
import com.falkenapps.macgyver.search.SearchActivity;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  implements  MainView{

    /*old main
    @BindString(R.string.main_message_welcome)
    String msgWelcome;
    @BindString(R.string.yes)
    String msgYes;
    @BindString(R.string.no)
    String msgNo;
    @BindString(R.string.doYouWantFinishApp)
    String msgDoYouWantFinishApp;
    @BindString(R.string.main_message_welcome_anonymous)
    String msgWelcomeAnonymous;
    @BindView(R.id.txtWelcomeMessage)
    TextView txtWelcomeMessage;
    @BindView(R.id.btnSignOff)
    Button btnSignOff;
    @BindView(R.id.imageViewUser)
    ImageView imgUser;
    @BindView(R.id.txtEmailMain)
    TextView txtEmail;*/


    private static final String ACTIVITY_TAG = Constant.TAG + ":MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    /*@BindView(R.id.left_drawer)
    ListView leftMenuDrawer;*/

    @BindString(R.string.main_leftMenu_option1)
    String menuOption1;
    @BindString(R.string.main_leftMenu_option2)
    String menuOption2;
    @BindString(R.string.main_searchingJob)
    String main_searchingJob;
    @BindString(R.string.main_nextToLocation)
    String main_nextToLocation;
    @BindString(R.string.main_nextToPostalCode)
    String main_nextToPostalCode;

    private ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;


    @Inject
    public MainPresenter mainPresenter;

    private User user;
    private PFAdapter mPFAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private  Context context;
    NavigationView navigationView;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_old);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainComponent mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(this)).build();
       // MainComponent mainComponent = DaggerMainComponent.builder().build();
        mainComponent.inject(this);

        /*Intent intent = new Intent(this, LocationService.class);
        intent.setAction("MainActivity");
        getBaseContext().stopService(intent);
        getBaseContext().startService(intent);*/



        setSupportActionBar(toolbar);
        //String[] menuOptions = new String[]{menuOption1,menuOption2,menuOption1,menuOption2};
        //leftMenuDrawer.setAdapter(new MenuDataAdapter(Arrays.asList(menuOptions),this));
        actionBar = getSupportActionBar();

        if (actionBar != null) {

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.main_drawer_open, R.string.main_drawer_close) {

            };
            drawerToggle.setDrawerIndicatorEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            drawerLayout.addDrawerListener(drawerToggle);
        }


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navview);
        navigationView.getMenu().findItem(R.id.menu_section_2_my_search).setChecked(true);


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {




                        switch (menuItem.getItemId()) {
                            case R.id.menu_section_1_favorites:
                                //fragment = new Fragment1();

                                //OJO FOR ALVAREZ
                                //startActivityForResult( new Intent(getBaseContext(),FavoritesActivity.class),Constant.RC_FAVORITES);


                                break;
                            case R.id.menu_section_2_my_search:
                                //fragment = new Fragment2();

                                break;

                            case R.id.menu_option_1_app_info:

                                startActivityForResult(new Intent(getBaseContext(),InfoActivity.class),Constant.RC_INFO);

                                break;
                            case R.id.menu_option_2_session:
                                if (user.isSigned()){
                                    mainPresenter.signOff();
                                    user = mainPresenter.getSignedUser();
                                    showSessionInfo(user);

                                }else{
                                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                                break;
                        }



                        drawerLayout.closeDrawers();

                        return true;
                    }
                });



        loadPages();




        user = mainPresenter.getSignedUser();


        showSessionInfo(user);
        registerSessionStarted(user);






        /*if (user.isSigned()) {
             showSessionInfo(user);

         }else
         {

             hideSessionInfo();
         }*/

        SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
        String locationType = settings.getString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_UNSELECT);
        if (locationType.equals(Constant.LOCATION_TYPE_UNSELECT)) {
            Log.d("DDD","ready to start Search");
            startActivityForResult(new Intent(this, SearchActivity.class), Constant.RC_NEW_SEARCH);
        }else{
            if (savedInstanceState == null) {
                // Then the application is not being reloaded
                Search search = loadLastSearch();
                if (search != null) {
                    Log.d(ACTIVITY_TAG, "loadLastSearch:" + search.toString());
                }
            }


        }

    }

    /*@OnClick(R.id.btnSignOff)
    public void handleSignOff(){
        mainPresenter.signOff();
        hideSessionInfo();



    }*/

    private void registerSessionStarted(User user){
        Date now = new Date();
        String sessionKey = String.valueOf(now.getTime()) + String.valueOf(Helper.randomInt(1000,9999));
        Log.d(ACTIVITY_TAG,"sessionKey:" + sessionKey);
        userSession = new UserSession(sessionKey);
        userSession.setStartDate(now);
        if (user.isSigned()){
            userSession.setUserEmail(user.getEmail());
        }else{
            userSession.setUserEmail(Constant.ANONYMOUS_EMAIL);
        }
    }

    private void registerSessionEnd(){
        Date now = new Date();
        userSession.setEndDate(now);
        long duration = now.getTime() - userSession.getStartDate().getTime();
        userSession.setDuration(duration);
        Log.d(ACTIVITY_TAG,"session_end:" + userSession.toString());
    }

    private void loadPages(){

        Log.d(ACTIVITY_TAG,"LoadPages");

        mPFAdapter = new PFAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            viewPager.setAdapter(mPFAdapter);
            viewPager.setOffscreenPageLimit(Constant.LIMIT_PAGE);
        }

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            if (tab != null) tab.select();
        }




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
       // showSessionInfo(user);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        Log.d(ACTIVITY_TAG,"onOptionsItemSelected:" + item.getItemId());
        /*switch (item.getItemId()) {

            case R.id.action_search:
                mSearchView.setIconified(false);
                return true;

        }*/
        return super.onOptionsItemSelected(item);
    }

    private void showSessionInfo(User user){
        /*txtWelcomeMessage.setText(String.format(msgWelcome, user.getName()));
        btnSignOff.setVisibility(View.VISIBLE);
        txtEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoURL()).into(imgUser);*/
        if (user.isSigned()) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
            View header = navigationView.getHeaderView(0);
            ImageView imageView = (ImageView) header.findViewById(R.id.userImage);
            Glide.with(this).load(user.getPhotoURL()).into(imageView);
            //Glide.with(this).load("https://randomuser.me/api/portraits/women/74.jpg").into(imageView);
            TextView textView = (TextView) header.findViewById(R.id.userName);
            textView.setText(user.getName());
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menu_option_2_session).setTitle(R.string.main_menu_option2b_signOut);
        }
        else{
            NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
            View header = navigationView.getHeaderView(0);
            ImageView imageView = (ImageView) header.findViewById(R.id.userImage);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.com_facebook_profile_picture_blank_square));
            TextView textView = (TextView) header.findViewById(R.id.userName);
            textView.setText(R.string.anonymous);
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menu_option_2_session).setTitle(R.string.main_menu_option2_signin);

        }


    }
    private void hideSessionInfo(){
        /*btnSignOff.setVisibility(View.INVISIBLE);
        imgUser.setVisibility(View.INVISIBLE);
        txtEmail.setVisibility(View.INVISIBLE);
        txtWelcomeMessage.setText(msgWelcomeAnonymous);*/

    }

    /*
    @OnItemClick(R.id.left_drawer)
    public void handleLeftMenuOnItemClick(int position){
        Log.d(ACTIVITY_TAG,"Position:" + position);
        if (position == 1) {
            Search search = new Search("Cerrajero");
            search.setRadius(20.0);
            FullGeoLocation fullGeoLocation = new FullGeoLocation("28080", 40.41808376, -3.68789826);
            search.setFullGeoLocation(fullGeoLocation);
            PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
            pageInteractor.changeFilter(search);
            SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            Log.d(ACTIVITY_TAG,"DELETE SHARE");
        }else{
            Search search = new Search("Fontanero");
            search.setRadius(20.0);
            FullGeoLocation fullGeoLocation = new FullGeoLocation("48950",43.30265077,-2.97692005);
            search.setFullGeoLocation(fullGeoLocation);
            PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
            pageInteractor.changeFilter(search);

        }


    }
    */

    @OnClick(R.id.btnNewSearch)
    public void handleBtnNewSearch(){
        Log.d(ACTIVITY_TAG,"New Search click");
        startActivityForResult(new Intent(this, SearchActivity.class),Constant.RC_NEW_SEARCH);

    }

    private Search loadLastSearch(){
        SharedPreferences settings = getSharedPreferences(Constant.PREFS_SEARCH, 0);
        String locationAddress = settings.getString(Constant.LOCATION_ADDRESS,"");
        String locationCodePostal = settings.getString(Constant.LOCATION_CODE_POSTAL,Constant.NULL_POSTAL_CODE);
        String locationType = settings.getString(Constant.LOCATION_TYPE, Constant.LOCATION_TYPE_UNSELECT);
        int jobCategoryID = settings.getInt(Constant.JOB_CATEGORY_ID,-1);
        int radius = settings.getInt(Constant.RADIUS_SETTING,Constant.RADIUS_SEARCH);
        String jobCategoryName = settings.getString(Constant.JOB_CATEGORY_NAME,"");
        String question = settings.getString(Constant.QUESTION,"");
        boolean isGuard = settings.getBoolean(Constant.GUARD,false);
        boolean  isHoliday = settings.getBoolean(Constant.HOLIDAY,false);
        Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
        Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

        Log.d("PRECISION","getLatitude_:" + latitude);
        Log.d("PRECISION","getLongitude_" + longitude);

        Log.d(ACTIVITY_TAG,"OnActivityResult");
        Log.d(ACTIVITY_TAG,"Job category id:" + jobCategoryID);
        Log.d(ACTIVITY_TAG,"Job category name:" + jobCategoryName);
        Log.d(ACTIVITY_TAG,"Location type:" + locationType);
        Log.d(ACTIVITY_TAG,"LocationCodePostal:" + locationCodePostal);
        Log.d(ACTIVITY_TAG,"LocationAddress:" + locationAddress);
        Log.d(ACTIVITY_TAG,"Latitude:" + latitude.toString());
        Log.d(ACTIVITY_TAG,"Longitude:" + longitude.toString());
        Log.d(ACTIVITY_TAG,"isGuard:" + isGuard);
        Log.d(ACTIVITY_TAG,"isHoliday:" + isHoliday);
        Log.d(ACTIVITY_TAG,"Question:" + question);
        if (!jobCategoryName.equals("")) {
           // Toast.makeText(this, String.format(main_searchingJob,jobCategoryName), Toast.LENGTH_LONG).show();
            //Helper.showToast(this,String.format(main_searchingJob,jobCategoryName),Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this, R.string.main_noJobFilter , Toast.LENGTH_LONG).show();
            return null;
        }

        if (locationType.equals(Constant.LOCATION_TYPE_UNSELECT)) {
            Toast.makeText(this, R.string.main_noGeoFilter, Toast.LENGTH_LONG).show();
            return null;


        }else{
            if (locationType.equals(Constant.LOCATION_TYPE_OTHER)){
                if (locationCodePostal.equals(Constant.NULL_POSTAL_CODE)){
                    Toast.makeText(this, R.string.main_postalCodeNotExists, Toast.LENGTH_LONG).show();
                    return null;
                }



            }

                /*Location targetLocation = new Location("");
                targetLocation.setLatitude(latitude);
                targetLocation.setLongitude(longitude);
                locationAddress = Helper.getAddress(targetLocation, this);
                Toast.makeText(this, String.format(main_nextToLocation,locationAddress), Toast.LENGTH_LONG).show();*/

                if (locationType.equals(Constant.LOCATION_TYPE_OTHER)){
                    //Toast.makeText(this, String.format(main_nextToPostalCode,locationCodePostal), Toast.LENGTH_LONG).show();

                }
                else{
                    //Toast.makeText(this, String.format(main_nextToLocation,locationAddress), Toast.LENGTH_LONG).show();
                }


        }



        Search search = new Search(jobCategoryName);
        search.setRadius(radius);
        if (!locationType.equals(Constant.LOCATION_TYPE_OTHER)){
            locationCodePostal = Constant.NULL_POSTAL_CODE;
        }
        FullGeoLocation fullGeoLocation = new FullGeoLocation(locationCodePostal, latitude, longitude);
        fullGeoLocation.setAddress(locationAddress);
        search.setFullGeoLocation(fullGeoLocation);
        search.setGuard(isGuard);
        if (user.isSigned()){
            search.setUserEmail(user.getEmail());
        }else{
            search.setUserEmail(Constant.ANONYMOUS_EMAIL);
        }

        search.setDate(new Date());

        return search;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RC_NEW_SEARCH){
            Log.d(ACTIVITY_TAG,"onActivityResult new search");
            /*Search search = new Search("Cerrajero");
            search.setRadius(20.0);
            FullGeoLocation fullGeoLocation = new FullGeoLocation("28080", 40.41808376, -3.68789826);
            search.setFullGeoLocation(fullGeoLocation);
            PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
            pageInteractor.changeFilter(search);*/




            Search search = loadLastSearch();

            if (search != null) {

                PageInteractorImpl pageInteractor = PageInteractorImpl.getInstance();
                pageInteractor.changeFilter(search);

                mainPresenter.writeToLogSearch(search);
            }


            //loadPages();

        }
        if (requestCode == Constant.RC_FAVORITES){
            Log.d(ACTIVITY_TAG,"Favorites END");
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menu_section_1_favorites).setChecked(false);
            menu.findItem(R.id.menu_section_2_my_search).setChecked(true);

        }

        if (requestCode == Constant.RC_INFO){
            Log.d(ACTIVITY_TAG,"Favorites END");
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menu_option_1_app_info).setChecked(false);
            menu.findItem(R.id.menu_section_2_my_search).setChecked(true);

        }
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if (user != null) {
            registerSessionEnd();
            mainPresenter.writeToLogSessionInfo(userSession);
        }
    }





}
