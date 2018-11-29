package net.forevents.foreventsandroid.Data.CreateUser.User

data class AppCreateUser(val ok:Boolean,
                         val message:String,//Si se crea correctamente llegará user_created
                         val id:String)

