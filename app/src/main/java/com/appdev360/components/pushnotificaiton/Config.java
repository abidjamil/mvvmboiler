package com.appdev360.components.pushnotificaiton;

/**
 * Created by jaffarraza on 02/05/2017.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "components_global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "components_registrationComplete";
    public static final String PUSH_NOTIFICATION = "components_pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "components_firebase";
}
