package co.appdev.boilerplate.di.module

import android.content.Context
import co.appdev.boilerplate.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import co.appdev.boilerplate.MyApplication
import co.appdev.boilerplate.data.local.database.UserPostDao
import co.appdev.boilerplate.di.ApplicationContext


@Module
class RoomModule{

    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, MyApplication.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserPostDao {
        return appDatabase.userPostDao()
    }


}