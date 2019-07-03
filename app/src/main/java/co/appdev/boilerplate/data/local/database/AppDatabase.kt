package co.appdev.boilerplate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.appdev.boilerplate.data.model.UserPost

@Database(entities = [UserPost::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userPostDao(): UserPostDao
}