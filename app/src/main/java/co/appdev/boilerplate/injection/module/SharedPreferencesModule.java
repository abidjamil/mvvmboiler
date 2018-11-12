package co.appdev.boilerplate.injection.module;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import co.appdev.boilerplate.util.Constants;
import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}