package co.appdev.lms.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserPostModel(@PrimaryKey val id: Int, val userId: Int, val title: String, val body: String) : Parcelable {
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
        val CREATOR: Parcelable.Creator<UserPostModel> = object : Parcelable.Creator<UserPostModel> {
            override fun createFromParcel(source: Parcel): UserPostModel = UserPostModel(source)
            override fun newArray(size: Int): Array<UserPostModel?> = arrayOfNulls(size)
        }
    }
}