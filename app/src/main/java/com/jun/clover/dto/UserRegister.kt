package com.jun.clover.dto

import android.os.Parcel
import android.os.Parcelable

data class UserRegister (
    val id : String,
    val name : String,
    val connApp : String,
    val email : String,
    var point : Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(connApp)
        parcel.writeString(email)
        parcel.writeInt(point)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserRegister> {
        override fun createFromParcel(parcel: Parcel): UserRegister {
            return UserRegister(parcel)
        }

        override fun newArray(size: Int): Array<UserRegister?> {
            return arrayOfNulls(size)
        }
    }

}