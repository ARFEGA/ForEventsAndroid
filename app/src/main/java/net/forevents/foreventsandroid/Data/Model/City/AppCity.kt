package net.forevents.foreventsandroid.Data.CreateUser.User

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AppEvents(

                        val name:String,
                        val latitude:Double,
                        val longitude:Double,
                        val description:String?,
                        val id:String,
                        val imgDescription:String?,
                        val imgUrl:String,
                        val begin_date: String,
                        val address: String,
                        val city: String,
                        val country: String
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
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
        parcel.writeString(name)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(description)
        parcel.writeString(id)
        parcel.writeString(imgDescription)
        parcel.writeString(imgUrl)
        parcel.writeString(begin_date)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppEvents> {
        override fun createFromParcel(parcel: Parcel): AppEvents {
            return AppEvents(parcel)
        }

        override fun newArray(size: Int): Array<AppEvents?> {
            return arrayOfNulls(size)
        }
    }
}
