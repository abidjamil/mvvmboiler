package co.appdev.boilerplate.di.module

import android.app.Application
import android.content.Context


import co.appdev.boilerplate.di.ApplicationContext

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }
}
