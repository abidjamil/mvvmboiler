package co.appdev.boilerplate.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import co.appdev.boilerplate.util.Constants
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    internal fun providesSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}