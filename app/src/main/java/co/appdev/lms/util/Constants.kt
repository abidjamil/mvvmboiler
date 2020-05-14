package co.appdev.lms.util

object Constants {
    val SPLASH_TIME_OUT = 1500L // Seconds
    val SHARED_PREFERENCES_NAME = "generalprefs"
    val THREAD_NAME = "SyncServiceThread"
    val START = "start"
    val STOP = "stop"
    val WAKE_LOCK_TAG = "PartialWakeLockCPU"
    val WAKE_LOCK_WIFI = "WifiWakeLock"
    val AppCenter_Secret = "981d6456-2197-4530-acb7-e418865356b9"

    enum class PersistenceKey {
        IS_LOGIN
    }

}
