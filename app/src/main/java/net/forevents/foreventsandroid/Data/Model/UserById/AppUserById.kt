package net.forevents.foreventsandroid.Data.Model.UserById

class AppUserById( val message: String,
                   val ok: Boolean,
                   val user: UserById) {

    inner class UserByIdApp(
        val _id: String,
        val address: String,
        val alias: String,
        val city: CityUserById,
        val country: String,
        val create_date: String,
        val delete_date: Any,
        val email: String,
        val events: List<Any>,
        val favorite_searches: List<Any>,
        val first_name: String,
        val last_name: String,
        val password: String,
        val profile: String,
        val province: String,
        val tokensFB: List<String>,
        val transactions: List<Any>,
        val validatedEmail: Boolean,
        val zip_code: String
    )

    inner class LocationUserByIdApp(
        val coordinates: List<Double>,
        val type: String
    )

    inner class CityUserByIdApp(
        val _id: String,
        val city: String,
        val country: String,
        val location: LocationUserById,
        val province: String,
        val users: List<Any>,
        val zip_code: String
    )
}