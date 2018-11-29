package net.forevents.foreventsandroid.Data.CreateUser.User

import com.google.gson.annotations.SerializedName

data class ApiCreateUser(
    @SerializedName("ok") val ok:Boolean,
    @SerializedName("message") val message : String,
    @SerializedName("user") val user:CreateUser)



data class CreateUser(
    @SerializedName("_id") val id:String

)