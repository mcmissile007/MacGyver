package com.falkenapps.macgyver.common;

import android.content.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FalkenApps:falken on 3/27/17.
 */

public final class Constant {

    public static final String TAG = "JB";
     public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    public static final String WRONG_PASSWORD = "wrongPassword";
    public static final String EMAIL_NOT_EXISTS= "EmailNotExits";
    public static final int MAX_LOGIN_ATTEMPTS = 2;

    public static final String PROFESSIONALS_DATA_REFERENCE = "professionalsDemo";
    public static final String PROFESSIONALS_DATA_REFERENCE_DEMO = "professionalsDemo";
    public static final String COMMENTS_DATA_REFERENCE = "comments";
    public static final String GEOFIRE_DATA_REFERENCE = "geoFire";
    public static final String FAVORITES_DATA_REFERENCE = "favorites";
    //public static final String GEOFIRE_DATA_REFERENCE = "geoFire2";
    public static final String LOGIN_LOG_DATA_REFERENCE = "login_log";
    public static final String SEARCH_LOG_DATA_REFERENCE = "search_log";
    public static final String GEO_LOG_DATA_REFERENCE = "geo_log";

    public static final String EMAIL_NOT_VERIFIED ="emailNotVerified";
    public static int  SIGN_UP_WITH_EMAIL_ERROR= 12000;
    public static int  SIGN_UP_WITH_EMAIL_OK = 12001;
    public static int SIGN_UP_WITH_EMAIL_INCOMPLETE_SENT_REMEMBER_PASSWORD_EMAIL = 12002;
    public static int  SIGN_UP_WITH_EMAIL_INCOMPLETE_CANCELED = 12003;
    public static int SIGN_UP_WITH_EMAIL_INCOMPLETE_ERROR_SENDING_REMEMBER_PASSWORD_EMAIL = 12004;

    public static final int LOCATION_THRESHOLD_TIME = 2 * 60 * 1000;

    public static int  NEW_SEARCH_SAVE= 12005;
    public static final String EMAIL_ALREADY_EXISTS = "ERROR";
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int RC_SIGN_IN_GOOGLE= 1107;
    public static final int RC_SIGN_UP_WITH_EMAIL=1108;
    public static final int RC_NEW_SEARCH=1109;
    public static final int RC_LOCATION=1110;
    public static final int RC_PROFESSIONAL=1111;
    public static final int RC_FAVORITES=1112;
    public static final int RC_INFO=1113;




    public static final int TIME_WELCOME_DIALOG_UPPER = 12000;
    public static final int TIME_WELCOME_DIALOG_LOWER= 6000;
    public static final int MIN_TIME_LOCATING_DIALOG = 8000;
    public static final int MAX_TIME_LOCATING_DIALOG = 12000;

    public static final int  MENU_FIRST = 1;
    public static final int MENU_SECOND = 2;

    public static final int MIN_DISTANCE_IN_METER = 0;
    public static final int MIN_TIME_IN_MS= 0;

    public static final int RADIUS_SEARCH = 10;

    public static final String RADIUS_SETTING = "radius";

    public static final int LIMIT_PAGE = 0;
    public static final int LONG_TIME_LOADING_IN_MS = 60000;
    public static final int SHORT_TIME_LOADING_IN_MS = 1000;
    public static final int MAX_TIME_LOADING_COMMENTS = 6000;
    public static final int MAX_TIME_LOADING_FAVORITES = 5000;

    public static final int MIN_TIME_SPLASH = 2000;
    public static final int MAX_TIME_SPLASH = 5000;
    public static final int LIMIT_SEARCH_RESULTS = 50;


    public static final String PREFS_SEARCH = "searchPref";
    public static final String JOB_CATEGORY_ID = "jobCategoryID";
    public static final String JOB_CATEGORY_NAME= "jobCategoryName";
    public static final String LOCATION_TYPE = "locationType";
    public static final String LOCATION_TYPE_OTHER = "locationTypeOther";
    public static final String LOCATION_TYPE_CURRENT = "locationTypeCurrent";
    public static final String LOCATION_TYPE_LAST = "locationTypeLast";
    public static final String LOCATION_TYPE_UNSELECT = "locationTypeUnselect";

    public static final String ANONYMOUS_EMAIL = "anonymous@email.net";

    public static final String DELETED = "deleted";
    public static final String FALKEN_NUMBER ="34693462292";



    public static final String LOCATION_ADDRESS = "locationAddress";
    public static final String LOCATION_CODE_POSTAL = "locationCodePostal";
    public static final String LOCATION_LATITUDE = "locationLatitude";
    public static final String LOCATION_LONGITUDE = "locationLongitude";

    public static final String LAST_LOCATION_ADDRESS = "lastLocationAddress";
    public static final String LAST_LOCATION_LATITUDE = "lastLocationLatitude";
    public static final String LAST_LOCATION_LONGITUDE = "lastLocationLongitude";


    public static final String GUARD= "guard";
    public static final String HOLIDAY= "holiday";
    public static final String QUESTION= "question";

    public static final String CODES_FILE = "codes.json";
    public static final String PROFESSIONALS_FILE = "professionals_utf8.json";

    public static final String NULL_POSTAL_CODE = "0";

    public static final String DEFAULT_JOB = "Cerrajero";





    public static final FullGeoLocation GEO_LOCATION = new FullGeoLocation("48980",43.31755801,-3.04391949);


    public static final List<String> JOBS = Collections.unmodifiableList(
            Arrays.asList(new String[] {"fontanero", "carpintero", "electricista", "cerrajero"}));

    public static final List<String> NAMES = Collections.unmodifiableList(
            Arrays.asList(new String[] {"Ramon", "Antonio", "Luis", "Alfonso",
                    "Pedro", "Miguel", "Jorge", "Ivan","Aitor","Julen","Elena","Silvia","Jose","Jesus","Daniel","Tomas", "Hugo", "Alejandro", "Pablo",
                    "Alvaro", "Adrian", "David", "Diego","Mario","Javier","Marcos","Paula","Sara","Carla","Ana"}));


    public static final List<String> SURNAMES = Collections.unmodifiableList(
            Arrays.asList(new String[] {"Garcia", "Lopez", "Perez", "Gonzalez",
                    "Sanchez", "Martinez", "Rodriguez", "Fernandez","Gomez","Martin","Hernandez","Ruiz","Diaz","Alvarez","Jimenez","Moreno", "Sanz", "Torres", "Suarez",
                    "Navarro", "Ramos", "Castro", "Gil","Flores","Vega","Rios","Salazar","Soto","Cortes","Ferrer"}));

    public static final List<String> EMAIL_PROVIDERS = Collections.unmodifiableList(
            Arrays.asList(new String[] {"gmail.com","yahoo.com","outlook.com","hotmail.es","outlook.es","hotmail.com"}));


    public static final List<String> COMMENTS = Collections.unmodifiableList(
            Arrays.asList(new String[] {"No lo recomiendo no es serio","Trabajo impecable","Ninguna queja, solucionó la avería y cobró lo presupuestado","No lo volvería a contratar, dejó muy sucia la casa","Muy recomendable, rapido ,efectivo y económico ","Todo bien, ningún problema"}));






}
