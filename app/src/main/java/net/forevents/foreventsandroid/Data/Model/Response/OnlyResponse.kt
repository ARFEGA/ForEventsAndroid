package net.forevents.foreventsandroid.Data.Model.Response

import java.io.Serializable

object OnlyResponse {


        data class ResultRecoveryPassword(val ok: Boolean,val message:String):Serializable
        data class Data(val children: List<Children>)
        data class Children(val data: Datas)
        data class Datas(val author: String,val thumbnail: String,val title: String)

}