package net.forevents.foreventsandroid.Data.Model.UserById

import com.google.gson.annotations.SerializedName

class ApiUserById(
    @SerializedName("message") val message: String,
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("user") val user: UserById
)

class UserById(
    @SerializedName("_id")val _id: String,
    @SerializedName("address")val address: String,
    @SerializedName("alias")val alias: String,
    @SerializedName("city")val city: CityUserById,
    @SerializedName("country")val country: String,
    @SerializedName("create_date")val create_date: String,
    @SerializedName("delete_date")val delete_date: Any,
    @SerializedName("email")val email: String,
    @SerializedName("events")val events: List<Any>,
    @SerializedName("favorite_searches")val favorite_searches: List<Any>,
    @SerializedName("first_name")val first_name: String,
    @SerializedName("last_name")val last_name: String,
    @SerializedName("password")val password: String,
    @SerializedName("profile")val profile: String,
    @SerializedName("province")val province: String,
    @SerializedName("tokensFB")val tokensFB: List<String>,
    @SerializedName("transactions")val transactions: List<Any>,
    @SerializedName("validatedEmail")val validatedEmail: Boolean,
    @SerializedName("zip_code")val zip_code: String
)
class LocationUserById(
    @SerializedName("coordinates")val coordinates: List<Double>,
    @SerializedName("type") val type: String
)
class CityUserById(
    @SerializedName("_id")val _id: String,
    @SerializedName("city")val city: String,
    @SerializedName("country")val country: String,
    @SerializedName("location")val location: LocationUserById,
    @SerializedName("province")val province: String,
    @SerializedName("users")val users: List<Any>,
    @SerializedName("zip_code")val zip_code: String
)