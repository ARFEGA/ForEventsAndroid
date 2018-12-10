package net.forevents.foreventsandroid.Data.CreateUser.User

import com.google.gson.annotations.SerializedName

class ApiUser(
    @SerializedName("ok") val ok:Boolean,
    @SerializedName("token") val token : String,
    @SerializedName("user") val user:User)



class User(
    @SerializedName("_id") val id:String
)