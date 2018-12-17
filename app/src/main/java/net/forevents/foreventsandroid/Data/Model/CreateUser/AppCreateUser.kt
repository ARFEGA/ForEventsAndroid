package net.forevents.foreventsandroid.Data.Model.CreateUser

data class AppCreateUser(val ok:Boolean,
                         val message:String,//Si se crea correctamente llegará user_created
                         val id:String)

