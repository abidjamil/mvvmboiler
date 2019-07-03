package co.appdev.boilerplate.data

import co.appdev.boilerplate.data.local.SharedPrefHelper
import co.appdev.boilerplate.data.remote.RemoteApi
import co.appdev.boilerplate.data.local.database.UserPostDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class DataManager @Inject
constructor(val remoteApi: RemoteApi,  val sharedPrefHelper: SharedPrefHelper,val userPostDao: UserPostDao)
