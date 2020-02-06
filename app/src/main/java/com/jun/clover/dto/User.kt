package com.jun.clover.dto

import android.os.Parcel
import android.os.Parcelable

data class User (
    val id : String,
    var name : String,
    var email : String,
    var point : Int,
    var bank : String?,
    var acc : String?,
    val connApp : String,
    var registerDate : String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeInt(point)
        parcel.writeString(bank)
        parcel.writeString(acc)
        parcel.writeString(connApp)
        parcel.writeString(registerDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}