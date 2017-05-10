package co.appdev.boilerplate.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

/**
 * Created by ahsan on 4/18/17.
 */
@Singleton
public class PreferencesDataHelper {

    private static final String GENERAL_PREFERENCE_NAME = "generalpreferences";

    public enum PersistenceKey {CLIENT_ID, SECRET_ID}


    public static void store(Context context, PersistenceKey key, String value) {

        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key.toString(), value);
        editor.apply();
    }

    public static String retrieve(Context context, PersistenceKey key) {

        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        return settings.getString(key.toString(), "null");
    }

    public static void clearPref(Context context){
        SharedPreferences settings = context.getSharedPreferences(GENERAL_PREFERENCE_NAME, 0);
        settings.edit().clear().apply();
    }


}
