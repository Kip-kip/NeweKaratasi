package com.ekaratasi.app;

/**
 * Created by Ravi Tamada on 28/09/16.
 * www.androidhive.info
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";



    //JSON URL
    public static final String DATA_URL = "https://www.ekaratasikenya.com/eKaratasi/Refubished/BackendAffairs/fetch_locations.php";

    //Tags used in the JSON String
    public static final String TAG_USERNAME = "id";
    public static final String TAG_NAME = "location";


    //JSON array name
    public static final String JSON_ARRAY = "result";

}
