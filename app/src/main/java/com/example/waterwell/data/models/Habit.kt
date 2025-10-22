package com.example.waterwell.data.models

import android.os.Parcel
import android.os.Parcelable

data class Habit(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    val description: String,
    val amount: Int = 0,
    val time: String = "",
    val isCompletedToday: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(amount)
        parcel.writeString(time)
        parcel.writeByte(if (isCompletedToday) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit = Habit(parcel)
        override fun newArray(size: Int): Array<Habit?> = arrayOfNulls(size)
    }
}
