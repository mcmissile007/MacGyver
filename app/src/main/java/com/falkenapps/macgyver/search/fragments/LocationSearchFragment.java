package com.falkenapps.macgyver.search.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.falkenapps.macgyver.R;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.search.interfaces.NavigateButtonClick;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSearchFragment extends Fragment {

    private static final String ACTIVITY_TAG = Constant.TAG + ":LocSearchFragment";
    private static final String ARG_PAGE = "ARG_PAGE";
    private int page;
    private Context context;
    private NavigateButtonClick navigateButtonClick;
    private OnFragmentInteractionListener mListener;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location bestLocation;
    private String bestAddress;
    private SharedPreferences settings;
    DiscreteSeekBar discreteSeekBar;

    @BindView(R.id.currentLocation)
    RadioButton currentLocation;
    @BindView(R.id.lastLocation)
    RadioButton lastLocation;
    @BindView(R.id.otherLocation)
    RadioButton otherLocation;
    @BindView(R.id.txtPostalCode)
    TextView txtPostalCode;


    public LocationSearchFragment() {
        // Required empty public constructor
    }

    public static LocationSearchFragment newInstance(int page){
        Log.d(ACTIVITY_TAG,"NewInstance LocationSearchFragment:" + page);
        LocationSearchFragment fragment = new LocationSearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
            Log.d(ACTIVITY_TAG,"OnCreate LocationSearchFragment:" + page);

        }
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.d(ACTIVITY_TAG, "onLocationChanged" + location.toString());
                if (Helper.isBetterLocation(location,bestLocation)){
                    bestLocation = location;
                    saveLocation(bestLocation);
                    Log.d(ACTIVITY_TAG, "BestLocation:" + bestLocation.toString());
                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(ACTIVITY_TAG,"OnDestroy");
        stopGeoLocation();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(ACTIVITY_TAG,"onStop");
        saveData();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(ACTIVITY_TAG,"onPause");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        String lastAddress = settings.getString(Constant.LOCATION_ADDRESS,"");
        String locationCodePostal = settings.getString(Constant.LOCATION_CODE_POSTAL,"");
        String locationType = settings.getString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_UNSELECT);
        Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LATITUDE, 0));
        Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LOCATION_LONGITUDE, 0));

        Log.d(ACTIVITY_TAG,"OnCreateView");
        Log.d(ACTIVITY_TAG,"LocationCodePostal:" + locationCodePostal);
        Log.d(ACTIVITY_TAG,"LocationID:" + locationType);
        Log.d(ACTIVITY_TAG,"LastAddress:" + lastAddress);
        Log.d(ACTIVITY_TAG,"Latitude:" + latitude.toString());
        Log.d(ACTIVITY_TAG,"Longitude:" + longitude.toString());


        if (locationType.equals(Constant.LOCATION_TYPE_UNSELECT)){

            Log.d(ACTIVITY_TAG,"NoLocation");
            lastLocation.setVisibility(View.GONE);
            currentLocation.setVisibility(View.VISIBLE);
            otherLocation.setVisibility(View.VISIBLE);

        }
        if(locationType.equals(Constant.LOCATION_TYPE_LAST)){
            Log.d(ACTIVITY_TAG,"LastLocation");
            lastLocation.setVisibility(View.VISIBLE);
            currentLocation.setVisibility(View.VISIBLE);
            otherLocation.setVisibility(View.VISIBLE);
            lastLocation.setChecked(true);

            lastLocation.setText(String.format(context.getResources().getString(R.string.search_fragment_location_nextTo),lastAddress));
            txtPostalCode.setVisibility(View.INVISIBLE);


        }
        if (locationType.equals(Constant.LOCATION_TYPE_CURRENT)){
            Log.d(ACTIVITY_TAG,"CurrentLocation");
            lastLocation.setVisibility(View.GONE);
            currentLocation.setVisibility(View.VISIBLE);
            otherLocation.setVisibility(View.VISIBLE);
            if (!lastAddress.equals("")) {
                lastLocation.setText(String.format(context.getResources().getString(R.string.search_fragment_location_nextTo),lastAddress));
                lastLocation.setVisibility(View.VISIBLE);
            }
            txtPostalCode.setVisibility(View.INVISIBLE);
            otherLocation.setChecked(false);
            currentLocation.setChecked(false);
            lastLocation.setChecked(true); //special case

        }
        if (locationType.equals(Constant.LOCATION_TYPE_OTHER)){
            Log.d(ACTIVITY_TAG,"Other Location");
            lastLocation.setVisibility(View.GONE);
            if (!lastAddress.equals("")) {
                lastLocation.setText(String.format(context.getResources().getString(R.string.search_fragment_location_nextTo),lastAddress));
                lastLocation.setVisibility(View.VISIBLE);
            }
            currentLocation.setVisibility(View.VISIBLE);
            otherLocation.setVisibility(View.VISIBLE);
            txtPostalCode.setVisibility(View.VISIBLE);
            txtPostalCode.setText(locationCodePostal);
            lastLocation.setChecked(false);
            currentLocation.setChecked(false);
            otherLocation.setChecked(true);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_location_search, container, false);
        ButterKnife.bind(this,view);
        settings = getContext().getSharedPreferences(Constant.PREFS_SEARCH, 0);
        discreteSeekBar =  (DiscreteSeekBar) view.findViewById(R.id.sb);

        int radius = settings.getInt(Constant.RADIUS_SETTING,(int)Constant.RADIUS_SEARCH);
        if (discreteSeekBar != null) {
            discreteSeekBar.setProgress(radius);
        }


        return view;
    }

    @OnClick(R.id.btnNextPage)
    public void handleNextPageClick(){
        Log.d(ACTIVITY_TAG,"LocationSearchFragment");

        if (navigateButtonClick != null){
            navigateButtonClick.nextButtonClicked(page);
        }


    }

    @OnClick(R.id.btnPrevPage)
    public void handlePrevPageClick(){

        Log.d(ACTIVITY_TAG,"LocationSearchFragment");
        if (navigateButtonClick != null){
            navigateButtonClick.prevButtonClicked(page);
        }


    }

    @OnClick({R.id.lastLocation, R.id.currentLocation, R.id.otherLocation})
    public void handleLocationClicked(RadioButton radioButton) {

        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.lastLocation:
                if (checked) {
                    Log.d(ACTIVITY_TAG, "lastLocation");
                    stopGeoLocation();
                    txtPostalCode.setVisibility(View.INVISIBLE);
                    Helper.hideKeyboardFrom(context,this.getView());


                }
                break;
            case R.id.currentLocation:
                if (checked) {
                    Log.d(ACTIVITY_TAG, "currentLocation");
                    txtPostalCode.setVisibility(View.INVISIBLE);
                    Helper.hideKeyboardFrom(context,this.getView());
                    getCurrentLocation();


                }
                break;
            case R.id.otherLocation:
                if (checked) {
                    Log.d(ACTIVITY_TAG, "otherLocation");
                    stopGeoLocation();
                    txtPostalCode.setVisibility(View.VISIBLE);



                }
                break;

        }

    }

    public void stopGeoLocation(){
        Log.d(ACTIVITY_TAG,"stopGeoLocation");
        if (locationManager != null && locationListener != null){
            Log.d(ACTIVITY_TAG,"Remove");
            locationManager.removeUpdates(locationListener);
        }
    }

    private void getCurrentLocation(){

        Log.d("DEBUG_LOCATION","getCurrentLocation");
        if (!Helper.isLocationEnabled(context)) {
            Log.d(ACTIVITY_TAG,"DISABLED Location");
            Log.d("DEBUG_LOCATION","DISABLED Location");
            buildAlertMessageNoGps();
            return;

        }

        showLocationProgressDialog();

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //request permission
            Log.d(ACTIVITY_TAG, "Request permission");

        } else {
            Log.d(ACTIVITY_TAG, "RequestLocationUpdates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constant.MIN_TIME_IN_MS, Constant.MIN_DISTANCE_IN_METER, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constant.MIN_TIME_IN_MS, Constant.MIN_DISTANCE_IN_METER, locationListener);
            Log.d(ACTIVITY_TAG,"getLastKnownLocation");
            Location lastKnownLocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocationNetwork != null) {
                if (Helper.isBetterLocation(lastKnownLocationNetwork,bestLocation)){

                    bestLocation = lastKnownLocationNetwork;
                    Log.d(ACTIVITY_TAG, "BestLocation:" + bestLocation.toString());
                }
            }

            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {

                if (Helper.isBetterLocation(lastKnownLocationGPS,bestLocation)){
                    bestLocation = lastKnownLocationGPS;
                    Log.d(ACTIVITY_TAG, "BestLocation:" + bestLocation.toString());
                }
            }
        }

        if (bestLocation != null){
            saveLocation(bestLocation);


        }

    }

    private void saveLocation(Location location){
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(Constant.LAST_LOCATION_LATITUDE,Double.doubleToLongBits(location.getLatitude()));
        editor.putLong(Constant.LAST_LOCATION_LONGITUDE,Double.doubleToLongBits(location.getLongitude()));
        editor.commit();
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.search_fragment_location_enableLocation)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                Constant.RC_LOCATION);
                        Log.d("DEBUG_LOCATION","startActivityForResult");
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        currentLocation.setChecked(false);
                        otherLocation.setChecked(true);
                        txtPostalCode.setVisibility(View.VISIBLE);
                        //txtPostalCode.setFocusable(true);

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void showLocationProgressDialog() {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage(context.getResources().getString(R.string.search_fragment_locating));
        progress.setCancelable(false);
        progress.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                stopGeoLocation();
                bestAddress = Helper.getAddress(bestLocation,context);


                if (bestAddress.equals("")){

                    currentLocation.setChecked(false);
                    Toast.makeText(context,context.getResources().getString(R.string.search_fragment_location_noAddress),Toast.LENGTH_LONG).show();
                    Toast.makeText(context,context.getResources().getString(R.string.search_fragment_location_suggestion_cp),Toast.LENGTH_LONG).show();
                    Toast.makeText(context,context.getResources().getString(R.string.search_fragment_location_suggestion_retry),Toast.LENGTH_LONG).show();

                }
                else {

                    currentLocation.setText(context.getResources().getString(R.string.search_fragment_location_nextTo)
                            + " " +  bestAddress);
                    lastLocation.setVisibility(View.VISIBLE);
                    currentLocation.setVisibility(View.VISIBLE);
                    otherLocation.setVisibility(View.VISIBLE);
                    lastLocation.setChecked(true);
                    lastLocation.setText(String.format(context.getResources().getString(R.string.search_fragment_location_nextTo), bestAddress));
                    currentLocation.setText(context.getResources().getString(R.string.search_fragment_location_currentLocation));
                    txtPostalCode.setText("");
                    txtPostalCode.setVisibility(View.INVISIBLE);
                }



            }
        }, Helper.randomInt(Constant.MIN_TIME_LOCATING_DIALOG,Constant.MAX_TIME_LOCATING_DIALOG));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("DEBUG_LOCATION","onActivityResult");

        if (requestCode == Constant.RC_LOCATION) {
            Log.d("DEBUG_LOCATION","onActivityResult Constant.RC_LOCATION");
            getCurrentLocation();

        }
    }


    private void saveData(){

        SharedPreferences.Editor editor = settings.edit();
        if (lastLocation.isChecked()){
            Log.d("JB_SAVE","lastLocationKnown");
            editor.putString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_LAST);
            if (bestLocation == null){
                String lastAddress = settings.getString(Constant.LOCATION_ADDRESS,"");
                if (!lastAddress.equals("")) {
                    Double latitude = Double.longBitsToDouble(settings.getLong(Constant.LAST_LOCATION_LATITUDE, 0));
                    Double longitude = Double.longBitsToDouble(settings.getLong(Constant.LAST_LOCATION_LONGITUDE, 0));
                    if (latitude != 0 && longitude != 0){
                        editor.putLong(Constant.LOCATION_LATITUDE,Double.doubleToLongBits(latitude));
                        editor.putLong(Constant.LOCATION_LONGITUDE,Double.doubleToLongBits(longitude));

                    }
                }

            }else{
                editor.putLong(Constant.LOCATION_LATITUDE,Double.doubleToLongBits(bestLocation.getLatitude()));
                editor.putLong(Constant.LOCATION_LONGITUDE,Double.doubleToLongBits(bestLocation.getLongitude()));
                if (bestAddress != null){
                    editor.putString(Constant.LOCATION_ADDRESS, bestAddress);

                }

            }
        }
        else if (otherLocation.isChecked()){
            Log.d("JB_SAVE","otherLocation by code");
            String cp = txtPostalCode.getText().toString();
            editor.putString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_OTHER);
            FullGeoLocation fullGeoLocation = Helper.getLocationFromPostalCode(context,cp);
            if (!fullGeoLocation.getCp().equals(Constant.NULL_POSTAL_CODE)) {
                Log.d("JB_SAVE","Code exists");
                editor.putLong(Constant.LOCATION_LATITUDE, Double.doubleToLongBits(fullGeoLocation.getLatitude()));
                editor.putLong(Constant.LOCATION_LONGITUDE, Double.doubleToLongBits(fullGeoLocation.getLongitude()));
                Log.d("PRECISION","getLatitude:" + fullGeoLocation.getLatitude());
                Log.d("PRECISION","getLongitude" + fullGeoLocation.getLongitude());
                editor.putString(Constant.LOCATION_CODE_POSTAL,cp);

            }else{
                Log.d("JB_SAVE","Code  not exists");
                editor.putString(Constant.LOCATION_CODE_POSTAL,Constant.NULL_POSTAL_CODE);
            }

        }
        else{
            Log.d("JB_SAVE","location Unselect");
            editor.putString(Constant.LOCATION_TYPE,Constant.LOCATION_TYPE_UNSELECT);
        }

        int radius = discreteSeekBar.getProgress();
        if (radius < 1){radius = 1;}
        if (radius > 25 ){radius = 25;}
        editor.putInt(Constant.RADIUS_SETTING,radius);



        editor.commit();


    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Activity activity = (Activity) context;
        navigateButtonClick = (NavigateButtonClick) activity;
        this.context = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(ACTIVITY_TAG,"onDetach");
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
