package net.forevents.foreventsandroid.Data.CreateUser.User

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import net.forevents.foreventsandroid.R.id.begin_date
import java.io.Serializable

data class AppCity(
                        val latitude:Double,
                        val longitude:Double,
                        val id:String,
                        val city: String,
                        val province:String,
                        val country: String,
                        val zip_codel:String
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(id)
        parcel.writeString(city)
        parcel.writeString(province)
        parcel.writeString(country)
        parcel.writeString(zip_codel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppCity> {
        override fun createFromParcel(parcel: Parcel): AppCity {
            return AppCity(parcel)
        }

        override fun newArray(size: Int): Array<AppCity?> {
            return arrayOfNulls(size)
        }
    }
}