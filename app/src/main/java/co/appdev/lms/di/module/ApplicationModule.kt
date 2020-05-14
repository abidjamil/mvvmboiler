package co.appdev.lms.di.module

import android.app.Application
import android.content.Context


import co.appdev.lms.di.ApplicationContext

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
