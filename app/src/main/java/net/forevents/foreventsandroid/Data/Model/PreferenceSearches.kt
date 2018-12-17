package net.forevents.foreventsandroid.Data.Model


import net.forevents.foreventsandroid.Data.Model.City.AppCity
import java.io.Serializable

data class PreferenceSearches(val favoriteCity:DataCity?,val favoriteRadius:String?,val favoriteEvents:List<String>?):Serializable {
    //val City = favoriteCity
    //val Radius = favoriteRadius
    //val Events = favoriteEvents
}
data class DataCity(
    val AppCity: AppCity,
    val cityAndProvince:String
)

