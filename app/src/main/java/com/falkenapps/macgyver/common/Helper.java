package com.falkenapps.macgyver.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.falkenapps.macgyver.R;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by FalkenApps:falken on 3/10/17.
 */

public final class Helper {




    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void showToast(Context context, String text, int time){
        Toast toast = Toast.makeText(context, text, time);
        View view = toast.getView();
        view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
        toast.show();

    }


    //for use with Activities
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //for use with Fragments
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public static int randomInt( int min, int max){
        //[min,max)
        Random random = new Random();
        return random.nextInt((max - min)) + min;

    }

    public static boolean getRandomBoolean(){
        Random random = new Random();
        //[min,max)
        int val  = random.nextInt(4) + 1;
        if (val < 3) return false;
        return true;
    }

    public static String  getRandomName(){
        return Constant.NAMES.get(Helper.randomInt(0,Constant.NAMES.size())).toString();


    }


    public static String  getRandomSurname(){
        return Constant.SURNAMES.get(Helper.randomInt(0,Constant.SURNAMES.size())).toString();

    }

    public static String getRandomPhotoURL(){
        //https://randomuser.me/api/portraits/thumb/men/99.jpg
        //https://randomuser.me/api/portraits/men/83.jpg
        //https://randomuser.me/api/portraits/med/men/83.jpg
        //https://randomuser.me/api/portraits/women/74.jpg

        return "";

    }

    public static String  getRandomProvider(){
        return  Constant.EMAIL_PROVIDERS.get(Helper.randomInt(0,Constant.EMAIL_PROVIDERS.size())).toString();

    }

    public static FullGeoLocation getRandomLocation(){
        List<FullGeoLocation> fullGeoLocations = new ArrayList<>();
        fullGeoLocations.add(new FullGeoLocation("48980",43.31755801,-3.04391949));
        fullGeoLocations.add(new FullGeoLocation("48950",43.30265077,-2.97692005));
        fullGeoLocations.add(new FullGeoLocation("28080",40.41808376,-3.68789826));
        int randomIndex = Helper.randomInt(0, fullGeoLocations.size());
        return fullGeoLocations.get(randomIndex);

    }

    public static double getRandomScore(){
        return Helper.randomInt(0,5) + new Random().nextDouble();

    }


    public static double getLowRandomScore(){
        return Helper.randomInt(0,2) + new Random().nextDouble();

    }

    public static double getHighRandomScore(){
        return Helper.randomInt(3,5) + new Random().nextDouble();

    }

    private static double getRandomPricePerHour(){
        return Helper.randomInt(10,50) + new Random().nextDouble();

    }
    public static double getRandomDeparturePrice(){
        return Helper.randomInt(0,50) + new Random().nextDouble();

    }

    public static List<String> getRandomJobs(){

        List<String> jobs = new ArrayList<>();

        for (int i=0 ; i< Helper.randomInt(1,3); i++){
            int randomIndex = Helper.randomInt(0, Constant.JOBS.size());
            String job =  Constant.JOBS.get(randomIndex).toString();
            jobs.add(job);

        }
       return jobs;

    }

    public static List<Comment> getRandomCommentList(){
        List<Comment> commentList = new ArrayList<>();
        int resultNumber = randomInt(0,6);
        for (int i = 0; i < resultNumber; i++){
            Comment comment = new Comment();
            String text = Constant.COMMENTS.get(randomInt(0,Constant.COMMENTS.size() - 1));
            comment.setText(text);
            if (text.contains("No ")){
                comment.setScore(getLowRandomScore());

            }
            else{
                comment.setScore(getHighRandomScore());
            }


            comment.setAuthorName(getRandomName() + " " + getRandomSurname());
            comment.setDate(new java.util.Date());

            commentList.add(comment);

        }


        return commentList;
    }

    public static List<Comment> getCommentList(){
        List<Comment> commentList = new ArrayList<>();
        int resultNumber = Constant.COMMENTS.size();
        for (int i = 0; i < resultNumber; i++){
            Comment comment = new Comment();
            String text = Constant.COMMENTS.get(i);
            comment.setText(text);
            if (text.contains("No ")){
                comment.setScore(getLowRandomScore());

            }
            else{
                comment.setScore(getHighRandomScore());
            }


            comment.setAuthorName(getRandomName() + " " + getRandomSurname());
            comment.setDate(new java.util.Date());

            commentList.add(comment);

        }


        return commentList;
    }

    public static  List<Professional> getRandomProfessionals(int number){

        List<Professional> result = new ArrayList<>();

        String randName;
        String randSurname;
        List<String> randomJobs;
        String name;
        String email;
        Professional professional;
        FullGeoLocation randomFullGeoLocation;

        for (int i = 0; i< number; i++){
            randomFullGeoLocation = Helper.getRandomLocation();
            randName = Helper.getRandomName() ;
            randSurname = Helper.getRandomSurname();
            name = randName + " " + randSurname;
            email = (randName + "." + randSurname + "@" + Helper.getRandomProvider()).toLowerCase();
            professional = new Professional(name,email);
            randomJobs = Helper.getRandomJobs();
            for (String job : randomJobs){
                professional.updateJobs(job,true);

            }
            professional.setFullGeoLocation(randomFullGeoLocation);
            professional.setScore(getRandomScore());
            professional.setKey(Helper.MD5_Hash(email));
            professional.setDeparturePrice(Helper.getRandomDeparturePrice());
            professional.setPricePerHour(Helper.getRandomPricePerHour());
            professional.setGuard(Helper.getRandomBoolean());
            result.add(professional);

        }

        return result;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);


            } catch (Settings.SettingNotFoundException e) {

                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > Constant.LOCATION_THRESHOLD_TIME;
        boolean isSignificantlyOlder = timeDelta < -Constant.LOCATION_THRESHOLD_TIME;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    public static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    public static FullGeoLocation getLocationFromPostalCode(Context context,String  postalCode){
        FullGeoLocation fullGeoLocation = new FullGeoLocation(Constant.NULL_POSTAL_CODE,0.0,0.0);
        String str_json = loadJSONFromAsset(context,Constant.CODES_FILE);
        try {
            JSONObject jsonObject = new JSONObject(str_json);
            Iterator<String> keySet = jsonObject.keys();
            while (keySet.hasNext()) {
                String cp = keySet.next();
                if (cp.equals(postalCode)) {
                    double latitude = jsonObject.getJSONObject(cp).getDouble("latitude");
                    double longitude = jsonObject.getJSONObject(cp).getDouble("longitude");
                    fullGeoLocation.setCp(postalCode);
                    fullGeoLocation.setLatitude(latitude);
                    fullGeoLocation.setLongitude(longitude);
                }

            }
        } catch (org.json.JSONException e) {
            Log.d(Constant.TAG, "JSONException:  " + e);

        }
        return fullGeoLocation;

    }

    private static String loadJSONFromAsset(Context context,String filename) {

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytes = is.read(buffer);
            is.close();
            if (bytes == size) return new String(buffer, "UTF-8");
            else return null;
        } catch (IOException ex) {
            Log.d(Constant.TAG, "IOException:  " + ex);
            return null;
        }

    }
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    public static String getAddress(Location location,Context context){
        List<Address> addresses = null;
        String retVal = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.

            Log.e(Constant.TAG, "IoException", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.

            Log.e(Constant.TAG, "Invalid_lat_long" + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            Log.e(Constant.TAG,"Error in address");

        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(Constant.TAG,"Address found");
            Log.i(Constant.TAG,addressFragments.toString());
            retVal = addressFragments.toString().replace("[","").replace("]", "");

        }

        return retVal;



    }



}
