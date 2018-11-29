package net.forevents.foreventsandroid.Data.CreateUser.RandomUser

data class RandomUser(
    val name: Name,
    val picture: Picture
)

data class Name(
    val last:String
)
data class Picture(
    val large:String,
    val medium:String,
    val thumbnail:String
)