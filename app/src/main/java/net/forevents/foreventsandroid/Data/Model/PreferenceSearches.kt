package net.forevents.foreventsandroid.Data.Model

import java.io.Serializable

data class PreferenceSearches(val favoriteCity:DataCity?,val favoriteRadius:String?,val favoriteEvents:List<String>?):Serializable {
    //val City = favoriteCity
    //val Radius = favoriteRadius
    //val Events = favoriteEvents
}
data class DataCity(
    val id:String,
    val cityAndProvince:String
)

