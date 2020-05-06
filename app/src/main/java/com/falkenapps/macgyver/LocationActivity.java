package com.falkenapps.macgyver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.falkenapps.macgyver.common.Comment;
import com.falkenapps.macgyver.common.Constant;
import com.falkenapps.macgyver.common.FullGeoLocation;
import com.falkenapps.macgyver.common.Helper;
import com.falkenapps.macgyver.common.Professional;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

//https://firebase.google.com/docs/database/android/read-and-write
public class LocationActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private GeoFire geoFire;
    private ValueEventListener valueEventListener;
    private static final String ACTIVITY_TAG = Constant.TAG + ":LocationActivity";
    private LocationManager locationManager;
    private LocationListener locationListener;
    private final String locationProviderNetwork = LocationManager.NETWORK_PROVIDER;
    private final String locationProviderGPS= LocationManager.GPS_PROVIDER;
    private Location bestLocation;

    @BindView(R.id.txtLog)
    TextView txtLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.d(ACTIVITY_TAG,location.toString());
                txtLog.setText(location.toString());
                if (Helper.isBetterLocation(location,bestLocation)){
                    txtLog.setText(txtLog.getText() + "New BestLocation[" + location.toString() + "]");
                    bestLocation = location;
                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };



        databaseReference = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference re= FirebaseDatabase.getInstance().getReference("geoFire");
        geoFire = new GeoFire(databaseReference.child("geoFire"));



       valueEventListener = new ValueEventListener() {  //use with child(profID)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Professional professional = dataSnapshot.getValue(Professional.class);
                if (professional != null) {
                    Log.d("JB", "onDataChange:" + professional.toString());
                    if (professional.getJobs().get("Fontanero") == true){
                        Log.d("JB", "Send to adapter:" + professional.toString());

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("JB",databaseError.getMessage());

            }
        };

        //geoFire.setLocation("franky", new FullGeoLocation(37.7853889, -122.4056973));
        /*ValueEventListener valueEventListener = new ValueEventListener() {  //use with child(profID)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Professional professional = dataSnapshot.getValue(Professional.class);
                if (professional != null) {
                    Log.d("JB", "onDataChange1:" + professional.toString());
                    Log.d("JB", "onDataChange2:" + professional.email);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("JB",databaseError.getMessage());

            }
        };*/
        /*ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if (data != null) {
                        String professionalID = data.getKey();
                        Log.d("JB", "key:" + professionalID);
                        Professional professional = data.getValue(Professional.class);
                        if (professional != null) {
                            //Log.d("JB", "onDataChange1:" + professional.toString());
                            Log.d("JB", "professional:" + professional.toString());
                        }
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("JB",databaseError.getMessage());

            }
        };*/
        //databaseReference.child(Constant.PROFESSIONALS).child("5e8364db36ff7de05fb48ec7b10c383c").addValueEventListener(valueEventListener);
       // databaseReference.child(Constant.PROFESSIONALS).orderByChild("guard").equalTo(true).addValueEventListener(valueEventListener);
        //databaseReference.child(Constant.PROFESSIONALS).orderByChild("jobs/1").equalTo("Hola").addValueEventListener(valueEventListener);
        //databaseReference.child(Constant.PROFESSIONALS).orderByChild("locations/48980").equalTo(true).addValueEventListener(valueEventListener);
    }

    @OnClick(R.id.btnClosest)
    public void handleBtnClosest(){
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(43.31755801, -3.04391949), 10.0);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                String message = String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude);
                Log.d("JB",message);

                databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(key).addValueEventListener(valueEventListener);

            }

            @Override
            public void onKeyExited(String key) {
                String message = String.format("Key %s is no longer in the search area", key);
                Log.d("JB",message);
                databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(key).removeEventListener(valueEventListener);

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                String message = String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude);
                Log.d("JB",message);

            }

            @Override
            public void onGeoQueryReady() {
                String message = "All initial data has been loaded and events have been fired!";
                Log.d("JB",message);

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                String message = "There was an error with this query: " + error;
                Log.d("JB",message);

            }
        });
    }


    @OnClick(R.id.btnGetLocation)
    public void handleGetLocation(){

        if (!Helper.isLocationEnabled(this)) {

            Log.d(ACTIVITY_TAG,"Disabled location");
            return;
        }


        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            //request permission
            Log.d(ACTIVITY_TAG,"Request permission");

        }
        else{
            Log.d(ACTIVITY_TAG,"getLastKnownLocation");
            Location lastKnownLocationNetwork = locationManager.getLastKnownLocation(locationProviderNetwork);
            if (lastKnownLocationNetwork != null) {
                Log.d(ACTIVITY_TAG, lastKnownLocationNetwork.toString());
                String data = "lastKnownLocation NetWork:"  + lastKnownLocationNetwork.getProvider()+ ""
                        + lastKnownLocationNetwork.getLatitude() + ":" + lastKnownLocationNetwork.getLongitude() + ":"
                        + lastKnownLocationNetwork.getTime() ;
                txtLog.setText(txtLog.getText() + "*****" + data);
                if (Helper.isBetterLocation(lastKnownLocationNetwork,bestLocation)){
                    txtLog.setText(txtLog.getText() + "New BestLocation[" + lastKnownLocationNetwork.toString() + "]");
                    bestLocation = lastKnownLocationNetwork;
                }
            }

            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(locationProviderGPS);
            if (lastKnownLocationGPS != null) {
                Log.d(ACTIVITY_TAG, lastKnownLocationGPS.toString());
                String data = "lastKnownLocation GPS:"  + lastKnownLocationGPS.getProvider()+ ""
                        + lastKnownLocationGPS.getLatitude() + ":" + lastKnownLocationGPS.getLongitude() + ":"
                        + lastKnownLocationGPS.getTime();
                txtLog.setText(txtLog.getText() + "*****" + data);
                if (Helper.isBetterLocation(lastKnownLocationGPS,bestLocation)){
                    txtLog.setText(txtLog.getText() + "New BestLocation[" + lastKnownLocationGPS.toString() + "]");
                    bestLocation = lastKnownLocationGPS;
                }
            }


        }

    }

    @OnClick(R.id.btnInsert)
    public void handleBtnInsertClicked(){

        Log.d(ACTIVITY_TAG,"handleBtnInsertClicked");
        String str_json = loadJSONFromAsset(Constant.PROFESSIONALS_FILE);
        /*byte[] ptext = str_json_prev.getBytes(ISO_8859_1);
        String str_json = new String(ptext, UTF_8);*/




        try{
            JSONObject jsonObject = new JSONObject(str_json);
            Iterator<String> keySet = jsonObject.keys();
            int count = 0;
            while (keySet.hasNext()) {
                String key = keySet.next();
                Log.d(ACTIVITY_TAG,key);
                double latitude =  jsonObject.getJSONObject(key).getDouble("latitude");
                double longitude =  jsonObject.getJSONObject(key).getDouble("longitude");
                String phone = jsonObject.getJSONObject(key).getString("phone");
                String activity = jsonObject.getJSONObject(key).getString("activity");
                String businessName = jsonObject.getJSONObject(key).getString("businessName");
                String businessAddress = jsonObject.getJSONObject(key).getString("businessAddress");
                String businessEmail = jsonObject.getJSONObject(key).getString("businessEmail");
                String smallBusinessAddress = jsonObject.getJSONObject(key).getString("smallBusinessAddress");
                String businessURL = jsonObject.getJSONObject(key).getString("businessURL");
                String photoURL = jsonObject.getJSONObject(key).getString("photoURL");
                String title = jsonObject.getJSONObject(key).getString("title");
                int guard = jsonObject.getJSONObject(key).getInt("guard");
                boolean isGuard = false;
                if (guard != 0) isGuard = true;

                String postalCode = jsonObject.getJSONObject(key).getString("postalCode");
                String city = jsonObject.getJSONObject(key).getString("city");
                String town = jsonObject.getJSONObject(key).getString("town");
                String region = jsonObject.getJSONObject(key).getString("region");
                String jobs = jsonObject.getJSONObject(key).getString("jobs");
                /*
                Log.d(ACTIVITY_TAG,"latitude:" + latitude);
                Log.d(ACTIVITY_TAG,"longitude:" + longitude);
                Log.d(ACTIVITY_TAG,"phone:" + phone);
                Log.d(ACTIVITY_TAG,"activity:" + activity);
                Log.d(ACTIVITY_TAG,"businessName:" + businessName);
                Log.d(ACTIVITY_TAG,"businessAddress:" + businessAddress);
                Log.d(ACTIVITY_TAG,"businessEmail:" + businessEmail);
                Log.d(ACTIVITY_TAG,"businessURL:" + businessURL);
                Log.d(ACTIVITY_TAG,"smallBusinessAddress:" + smallBusinessAddress);
                Log.d(ACTIVITY_TAG,"photoURL:" + photoURL);
                Log.d(ACTIVITY_TAG,"title:" + title);
                Log.d(ACTIVITY_TAG,"guard:" + isGuard);
                Log.d(ACTIVITY_TAG,"postalCode:" + postalCode);
                Log.d(ACTIVITY_TAG,"city:" + city);
                Log.d(ACTIVITY_TAG,"town:" + town);
                Log.d(ACTIVITY_TAG,"region:" + region);
                Log.d(ACTIVITY_TAG,"jobs:" + jobs);
                */
                HashMap<String,Boolean> jobsMap = new HashMap<>();
                String[] jobsList = jobs.split("\\|");
                for (String job: jobsList){
                    jobsMap.put(job,true);
                }

                Professional professional = new Professional();
                professional.setDistance(0);
                professional.setKey(Helper.MD5_Hash(phone));
                professional.setPhone(phone);
                professional.setDeparturePrice(0);
                professional.setPricePerHour(Helper.randomInt(10,30));
                professional.setGuard(isGuard);
                professional.setDescription(title); //not yet description
                professional.setEmail(businessEmail);
                professional.setFullGeoLocation(new FullGeoLocation(postalCode,latitude,longitude));
                professional.setJobs(jobsMap);
                professional.setName(businessName);
                professional.setScore(Helper.getRandomScore());
                professional.setTitle(title);
                professional.setActivity(activity);
                professional.setBusinessAddress(businessAddress);
                professional.setBusinessURL(businessURL);
                professional.setRegion(region);
                professional.setSmallBusinessAddress(smallBusinessAddress);
                professional.setMacGyver(false);
                if (isGuard){
                    professional.setAllDaysOfYear(Helper.getRandomBoolean());
                }else{
                    professional.setAllDaysOfYear(false);

                }
                professional.setCity(city);
                professional.setCountry("España");
                if (photoURL.equals("")) {
                    String random_photo = "https://randomuser.me/api/portraits/men/"  + Helper.randomInt(1,99)   + ".jpg";
                    professional.setPhotoURL(random_photo);

                }
                else{
                    professional.setPhotoURL(photoURL);
                }

                professional.setTown(town);
                Log.d(ACTIVITY_TAG,professional.toString());



                if (true) {
                    databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE_DEMO).child(professional.getKey()).setValue(professional);
                    geoFire.setLocation(professional.getKey(),new GeoLocation(professional.getFullGeoLocation().getLatitude(),professional.getFullGeoLocation().getLongitude()));
                }
                count += 1;






            }


        }
        catch (org.json.JSONException e) {
            Log.d(ACTIVITY_TAG, "JSONException:  " + e);
        }

    }


    @OnClick(R.id.btnAddComments)
    public void handleAddComments(){
        List<Comment> comments = Helper.getCommentList();
        int count = 0;
        for (Comment comment : comments) {
            Log.d("JB_COMMENTS",comment.toString());
            databaseReference.child(Constant.COMMENTS_DATA_REFERENCE).child("demoProfessional").child(Integer.toString(count)).setValue(comment);
            count += 1;
        }

    }


    @OnClick(R.id.btnLoadCodes)
    public void handleLoadCodesClicked(){
        Log.d(ACTIVITY_TAG,"loadCodesClicked");
        String str_json = loadJSONFromAsset(Constant.CODES_FILE);
        try {
            JSONObject jsonObject = new JSONObject(str_json);
            Iterator<String> keySet = jsonObject.keys();

            while (keySet.hasNext()) {
              String cp = keySet.next();
              //Log.d(ACTIVITY_TAG,cp);
              double latitude =  jsonObject.getJSONObject(cp).getDouble("latitude");
              double longitude =  jsonObject.getJSONObject(cp).getDouble("longitude");
              if (cp.equals("48980")) {
                  Log.d(ACTIVITY_TAG, "Latitude:" + latitude);
                  Log.d(ACTIVITY_TAG, "Longitude:" + longitude);
              }
              if (latitude < 20.00 || latitude > 50.0){
                  Log.d(ACTIVITY_TAG, "ERROR:" + cp);
              }

              if (longitude < -20.0 || longitude > 20.0){
                  Log.d(ACTIVITY_TAG, "ERROR:" + cp);
                  Log.d(ACTIVITY_TAG, "Latitude:" + latitude);
                  Log.d(ACTIVITY_TAG, "Longitude:" + longitude);
               }



            }

        } catch (org.json.JSONException e) {
            Log.d(ACTIVITY_TAG, "JSONException:  " + e);

        }

    }

    private String loadJSONFromAsset(String filename) {

        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytes = is.read(buffer);
            is.close();
            if (bytes == size) return new String(buffer, "UTF-8");
            else return null;
        } catch (IOException ex) {
            Log.d(ACTIVITY_TAG, "IOException:  " + ex);
            return null;
        }

    }

    @OnClick(R.id.btnAddress)
    public void handleBtnAddressClick(){
        getAddress(bestLocation);
    }

    private void getAddress(Location location){
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.

            Log.e(ACTIVITY_TAG, "IoException", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.

            Log.e(ACTIVITY_TAG, "Invalid_lat_long" + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
           Log.e(ACTIVITY_TAG,"Error in address");

        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(ACTIVITY_TAG,"Address found");
            Log.i(ACTIVITY_TAG,addressFragments.toString());
            txtLog.setText(addressFragments.toString());

        }


}

    @OnClick(R.id.btnStop)
    public void handleStopLocation(){
        locationManager.removeUpdates(locationListener);
        txtLog.setText("");
    }







    @OnClick(R.id.btnStart)
    public void handleBtnStart(){


        if (!Helper.isLocationEnabled(this)) {
            Log.d(ACTIVITY_TAG,"DISABLED Location");
            buildAlertMessageNoGps();
            return;


        }


            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //request permission
                Log.d(ACTIVITY_TAG, "Request permission");

            } else {
                Log.d(ACTIVITY_TAG, "requestLocationUpdates");
                locationManager.requestLocationUpdates(locationProviderNetwork, 0, 0, locationListener);
                locationManager.requestLocationUpdates(locationProviderGPS, 0, 0, locationListener);


            }






    }


    @OnClick(R.id.btnNew)
    public void handleBtnNew(){
        String professionalKey;
        List<Professional> professionals;


        professionals = Helper.getRandomProfessionals(10);


        for (Professional professional: professionals){

            professionalKey = Helper.MD5_Hash(professional.getEmail());
            databaseReference.child(Constant.PROFESSIONALS_DATA_REFERENCE).child(professionalKey).setValue(professional);
            geoFire.setLocation(professionalKey,new GeoLocation(professional.getFullGeoLocation().getLatitude(),professional.getFullGeoLocation().getLongitude()));

        }













    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
