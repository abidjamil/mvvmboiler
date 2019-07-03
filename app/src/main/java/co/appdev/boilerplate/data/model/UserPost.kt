package co.appdev.boilerplate.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPost(@PrimaryKey val id: Int, val userId: Int, val title: String, val body: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(userId)
        writeString(title)
        writeString(body)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserPost> = object : Parcelable.Creator<UserPost> {
            override fun createFromParcel(source: Parcel): UserPost = UserPost(source)
            override fun newArray(size: Int): Array<UserPost?> = arrayOfNulls(size)
        }
    }
}