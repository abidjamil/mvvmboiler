package co.appdev.boilerplate.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.appdev.boilerplate.data.model.UserPost

@Dao
interface UserPostDao {

    @Query("SELECT * FROM userpost")
    fun getAll():List<UserPost>

    @Query("SELECT * FROM userpost WHERE id=:postId")
    fun get(postId: Int): UserPost

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserPost>)
}