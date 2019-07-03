package co.appdev.boilerplate.data.local

interface UserDataHelper {

    fun getIsLogin(): Boolean

    fun storeIsLogin(isLogin: Boolean)

    fun clearAllPref()
}
