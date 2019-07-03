package co.appdev.boilerplate

import android.app.Application
import android.content.Context
import co.appdev.boilerplate.di.component.ApplicationComponent
import co.appdev.boilerplate.di.component.DaggerApplicationComponent
import co.appdev.boilerplate.di.module.ApplicationModule
import co.appdev.boilerplate.di.module.NetworkModule
import co.appdev.boilerplate.di.module.RoomModule
import co.appdev.boilerplate.di.module.SharedPreferencesModule

class MyApplication : Application() {

    private var mApplicationComponent: ApplicationComponent? = null

    val component: ApplicationComponent?
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                    .networkModule(NetworkModule())
                    .applicationModule(ApplicationModule(this))
                    .sharedPreferencesModule(SharedPreferencesModule())
                    .roomModule(RoomModule())
                    .build()
            }
            return mApplicationComponent
        }

    companion object {

        const val DATABASE_NAME = "post.db"

            operator fun get(context: Context): MyApplication {
            return context.applicationContext as MyApplication
        }
    }

}