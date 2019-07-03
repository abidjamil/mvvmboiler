package co.appdev.boilerplate.data.local

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

import co.appdev.boilerplate.util.Constants

@Singleton
class SharedPrefHelper @Inject
internal constructor(private val preferences: SharedPreferences) : UserDataHelper {

    private fun store(key: Constants.PersistenceKey, value: String) {
        preferences.edit().putString(key.toString(), value).apply()
    }

    private fun retrieve(key: Constants.PersistenceKey): String? {
        return preferences.getString(key.toString(), "")
    }

    override fun storeIsLogin(isLogin: Boolean) {
        store(Constants.PersistenceKey.IS_LOGIN, isLogin.toString())
    }

    override fun getIsLogin(): Boolean {
        return java.lang.Boolean.parseBoolean(retrieve(Constants.PersistenceKey.IS_LOGIN))
    }

    override fun clearAllPref() {
        preferences.edit().clear().apply()
    }

}