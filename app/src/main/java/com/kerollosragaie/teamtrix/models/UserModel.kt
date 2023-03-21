package com.kerollosragaie.teamtrix.models

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    val id:String="",
    val name:String="",
    val email:String="",
    val image:String="",
    val mobile:Long=0L,
    val fcmToken:String="",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!
    )
    override fun describeContents(): Int =0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest){
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(image)
        writeLong(mobile)
        writeString(fcmToken)
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}
