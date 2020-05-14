package co.appdev.lms.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

import javax.inject.Inject
import javax.inject.Singleton

import co.appdev.lms.util.Constants

@Singleton
class SharedPrefHelper @Inject
internal constructor(private val preferences: SharedPreferences) : UserDataHelper {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private fun getSharedPref(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            Constants.SHARED_PREFERENCES_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun store(context: Context, key: Constants.PersistenceKey, value: String) {
        getSharedPref(context).let {
            it.edit()
                .putString(key.toString(), value)
                .apply()
        }
    }


    private fun retrieve(key: Constants.PersistenceKey): String? {
        return preferences.getString(key.toString(), "")
    }

    override fun storeIsLogin(context: Context, isLogin: Boolean) {
        store(context, Constants.PersistenceKey.IS_LOGIN, isLogin.toString())
    }

    override fun getIsLogin(): Boolean {
        return java.lang.Boolean.parseBoolean(retrieve(Constants.PersistenceKey.IS_LOGIN))
    }

    override fun clearAllPref() {
        preferences.edit().clear().apply()
    }

}