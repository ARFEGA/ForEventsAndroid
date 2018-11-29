package net.forevents.foreventsandroid.Data.CreateUser.RandomUser


import android.os.Parcel
import android.os.Parcelable



data class UserEntity (val first_name:String,val picture:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(picture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }
}
//val userId:Long,
                        //var first_name:String
                        //val last_name:String,
                        //var picture:String
                        //val email:String



/*
 first_name: { type: String, index: true },
last_name: { type: String, index: true },
email: { type: String, index: true },
password: String,
address: String,
city: String,
zip_code: String,
province: String,
country: String,
password: String,
birthday: Date,
gender: String,
create_date: Date,
delete_date: Date,
alias: String,
idn: String,
company_name: String,
mobile_number: String,
phone_number: String,
profile: String,
*/

