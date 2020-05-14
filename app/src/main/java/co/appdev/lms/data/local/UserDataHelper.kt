package co.appdev.lms.data.local

import android.content.Context

interface UserDataHelper {

    fun getIsLogin(): Boolean

    fun storeIsLogin(context: Context, isLogin: Boolean)

    fun clearAllPref()
}
