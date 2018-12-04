package net.forevents.foreventsandroid.Data.CreateUser.User

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import net.forevents.foreventsandroid.R.id.begin_date
import java.io.Serializable

data class AppEventType(

                        val id:String,
                        val name: String

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppEventType> {
        override fun createFromParcel(parcel: Parcel): AppEventType {
            return AppEventType(parcel)
        }

        override fun newArray(size: Int): Array<AppEventType?> {
            return arrayOfNulls(size)
        }
    }
}