package co.appdev.lms

import android.app.Application
import android.content.Context
import co.appdev.lms.di.component.ApplicationComponent
import co.appdev.lms.di.component.DaggerApplicationComponent
import co.appdev.lms.di.module.ApplicationModule
import co.appdev.lms.di.module.NetworkModule
import co.appdev.lms.di.module.SharedPreferencesModule

class MyApplication : Application() {

    private var mApplicationComponent: ApplicationComponent? = null

    val component: ApplicationComponent?
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                    .networkModule(NetworkModule())
                    .applicationModule(ApplicationModule(this))
                    .sharedPreferencesModule(SharedPreferencesModule())
                    .build()
            }
            return mApplicationComponent
        }

    companion object {
            operator fun get(context: Context): MyApplication {
            return context.applicationContext as MyApplication
        }
    }

}