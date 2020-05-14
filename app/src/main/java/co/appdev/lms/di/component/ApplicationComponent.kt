package co.appdev.lms.di.component

import co.appdev.lms.data.DataManager
import co.appdev.lms.di.module.ApplicationModule
import co.appdev.lms.di.module.NetworkModule
import co.appdev.lms.di.module.SharedPreferencesModule
import co.appdev.lms.services.SyncService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApplicationModule::class, SharedPreferencesModule::class])
interface ApplicationComponent {

    fun dataManager(): DataManager

    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */

    fun inject(syncService: SyncService)


}

