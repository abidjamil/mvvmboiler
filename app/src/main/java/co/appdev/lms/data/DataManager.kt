package co.appdev.lms.data

import co.appdev.lms.data.local.SharedPrefHelper
import co.appdev.lms.data.remote.RemoteApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class DataManager @Inject
constructor(val remoteApi: RemoteApi,  val sharedPrefHelper: SharedPrefHelper)
