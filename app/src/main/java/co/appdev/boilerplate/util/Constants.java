package co.appdev.boilerplate.util;

public class Constants {
    public static final Integer SPLASH_TIME_OUT = 3; // Seconds
    public static final String SHARED_PREFERENCES_NAME = "generalprefs";
    public enum PersistenceKey {IS_LOGIN, TOKEN, LAST_SYNC_DATE, SERVICE_RUNNING}
    public static final String THREAD_NAME = "SyncServiceThread";
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String WAKE_LOCK_TAG = "PartialWakeLockCPU";
    public static final String WAKE_LOCK_WIFI = "WifiWakeLock";

}
