package net.forevents.foreventsandroid.Data.Model.Transactions

import com.google.gson.annotations.SerializedName
import net.forevents.foreventsandroid.Data.CreateUser.User.ResultEvents

data class ApiGetTransactions(
    @SerializedName("ok")val ok: Boolean,
    @SerializedName("result") val result: List<ResultTransactions>
)


data class ResultTransactions(
    @SerializedName("_id")val id: String,
    @SerializedName("create_date")val create_date: String,
    @SerializedName("event") val event: EventTransactions
)



data class EventTransactions(

    val _id: String,
    val active: Boolean,
    val address: String,
    val begin_date: String,
    val city: String,
    val create_date: String,
    val description: String,
    val end_date: String,
    val event_type: EventType,
    val free: Boolean,
    val location: Location,
    val media: List<Media>,
    val name: String,
    val notified: Boolean,
    val organizer: String,
    val price: Int,
    val province: String,
    val transactions: List<String>,
    val users: List<Any>,
    val zip_code: String
)

data class EventType(
    val _id: String,
    val name: String
)
data class Location(
    val coordinates: List<Double>,
    val type: String
)

data class Media(
    val _id: String,
    val description: String,
    val event: String,
    val media_type: String,
    val name: String,
    val poster: Boolean,
    val url: String,
    val user: String
)