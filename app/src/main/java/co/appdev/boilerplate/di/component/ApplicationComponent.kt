package co.appdev.boilerplate.di.component

import co.appdev.boilerplate.data.DataManager
import co.appdev.boilerplate.data.local.SharedPrefHelper
import co.appdev.boilerplate.di.module.ApplicationModule
import co.appdev.boilerplate.di.module.NetworkModule
import co.appdev.boilerplate.di.module.RoomModule
import co.appdev.boilerplate.di.module.SharedPreferencesModule
import co.appdev.boilerplate.services.SyncService
import co.appdev.boilerplate.ui.list.UserPostListViewModel
import co.appdev.boilerplate.ui.list.UserPostViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ApplicationModule::class, RoomModule::class, SharedPreferencesModule::class])
interface ApplicationComponent {

    fun dataManager(): DataManager

    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(postListViewModel: UserPostListViewModel)

    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param postViewModel PostViewModel in which to inject the dependencies
     */
    fun inject(postViewModel: UserPostViewModel)

    fun inject(syncService: SyncService)


}

