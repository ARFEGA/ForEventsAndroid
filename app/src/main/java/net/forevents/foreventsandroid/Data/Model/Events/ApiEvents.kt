package net.forevents.foreventsandroid.Data.CreateUser.User

import com.google.gson.annotations.SerializedName




data class ApiEvents(
    @SerializedName("ok") val ok:Boolean,
    @SerializedName("result") val result : List<ResultEvents>)


data class ResultEvents(
    @SerializedName("name") val name:String,
    @SerializedName("location") val location:Location,
    @SerializedName("description") val description: String,
    @SerializedName("_id") val id: String,
    @SerializedName("transactions") val transactions: List<TransactionsInEvents>,
    @SerializedName("media") val media: List<Media>,
    @SerializedName("begin_date") val begin_date: String,
    @SerializedName("adress") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("province") val province: String,
    @SerializedName("country") val country: String

    )

data class TransactionsInEvents(
    @SerializedName("_id") val idTrans:String,
    @SerializedName("user") val user:String
)

data class Media(
    @SerializedName("description") val description:String,
    @SerializedName("url") val url:String
)
data class Location(
    @SerializedName("coordinates") val coordinates: List<Double>
    //val type: String
)


data class EventType(
    val name: String
)

data class Result__(
    val __v: Int,
    val _id: String,
    val active: Boolean,

    val create_date: String,
    val description: String,
    val end_date: String,
    val event_type: EventType,
    val free: Boolean,
    val indoor: Boolean,
    val location: Location,
    val max_visitors: Int,
    val media: List<Media>,
    val min_age: Int,
    val name: String,
    val organizer: String,
    val price: Int,
    val province: String,
    val transaction: List<Any>,
    val transactions: List<Any>,
    val user: List<Any>,
    val users: List<Any>,
    val zip_code: String
)









