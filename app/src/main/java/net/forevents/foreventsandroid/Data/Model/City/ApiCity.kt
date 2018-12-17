package net.forevents.foreventsandroid.Data.Model.City

import com.google.gson.annotations.SerializedName




data class ApiCity(
    @SerializedName("ok") val ok:Boolean,
    @SerializedName("result") val result : List<ResultCities>)


data class ResultCities(
    @SerializedName("location") val location:LocationCity,
    @SerializedName("_id") val id: String,
    @SerializedName("city") val city:String,
    @SerializedName("province") val province: String,
    @SerializedName("country") val country: String,
    @SerializedName("zip_code") val zip_code: String
    )


data class LocationCity(
    @SerializedName("coordinates") val coordinates: List<Double>

)










