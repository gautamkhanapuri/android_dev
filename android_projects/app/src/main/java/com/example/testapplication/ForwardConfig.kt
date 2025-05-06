package com.example.testapplication

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forwardconfig")
data class ForwardConfig(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val message: String?,
    val fromPhone: String?,
    val email: String?,
    val telegram: String?,
    val active: Int?,
    val count: Int?
) :Parcelable {
    // implementing parcelable interface
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeLong(id)
        parcel.writeString(message)
        parcel.writeString(fromPhone)
        parcel.writeString(email)
        parcel.writeString(telegram)
        parcel.writeInt(active!!)
        parcel.writeInt(count!!)
    }

    companion object CREATOR : Parcelable.Creator<ForwardConfig> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): ForwardConfig {
            return ForwardConfig(parcel)
        }

        override fun newArray(size: Int): Array<ForwardConfig?> {
            return arrayOfNulls(size)
        }
    }
}
