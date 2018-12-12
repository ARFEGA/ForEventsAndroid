package net.forevents.foreventsandroid.Data.Model.Transactions

import android.os.Parcel
import android.os.Parcelable

class AppTransactions(
    val transactionId: String,//trnsaction
    val eventId: String,//Event
    val eventName: String,
    val address: String,
    val begin_date: String,
    val city: String,
    val province: String,
    val url: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(transactionId)
        parcel.writeString(eventId)
        parcel.writeString(eventName)
        parcel.writeString(address)
        parcel.writeString(begin_date)
        parcel.writeString(city)
        parcel.writeString(province)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppTransactions> {
        override fun createFromParcel(parcel: Parcel): AppTransactions {
            return AppTransactions(parcel)
        }

        override fun newArray(size: Int): Array<AppTransactions?> {
            return arrayOfNulls(size)
        }
    }
}