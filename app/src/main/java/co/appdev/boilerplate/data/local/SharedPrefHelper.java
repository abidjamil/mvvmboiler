package co.appdev.boilerplate.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.util.Constants;

@Singleton
public class SharedPrefHelper implements UserDataHelper {

    private SharedPreferences preferences;

    @Inject
    SharedPrefHelper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private void store(Constants.PersistenceKey key, String value) {
        preferences.edit().putString(key.toString(), value).apply();
    }

    private String retrieve(Constants.PersistenceKey key) {
        return preferences.getString(key.toString(), "");
    }

    @Override
    public void storeIsLogin(boolean is_login) {
        store(Constants.PersistenceKey.IS_LOGIN, String.valueOf(is_login));
    }

    @Override
    public boolean getIsLogin() {
        return Boolean.parseBoolean(retrieve(Constants.PersistenceKey.IS_LOGIN));
    }

    @Override
    public void clearAllPref() {
        preferences.edit().clear().apply();
    }

    public void storeToken(String token) {
        store(Constants.PersistenceKey.TOKEN, token);
    }

    public String getToken() {
        return retrieve(Constants.PersistenceKey.TOKEN);
    }

}