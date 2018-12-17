package net.forevents.foreventsandroid.Data.Model.EventType
import com.google.gson.annotations.SerializedName




data class ApiEventType(
    @SerializedName("ok") val ok:Boolean,
    @SerializedName("result") val result : List<ResultEventType>)


data class ResultEventType(

    @SerializedName("_id") val id: String,
    @SerializedName("name") val name:String
    )









