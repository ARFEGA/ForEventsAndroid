package net.forevents.foreventsandroid.Data.CreateUser.User


import net.forevents.foreventsandroid.Data.CreateUser.User.ApiUser
import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.Data.CreateUser.Mapper

class OutAppUserMapper :
    Mapper<ApiUser, AppUser> {
    override fun transform(input: ApiUser): AppUser =
        AppUser(
            input.ok,
            input.token,
            input.user.id

        )
        //AppUser(input.ok,input.token,input.user._id,input.user.email,input.user.password)

    override fun transformList(inputList: List<ApiUser>): List<AppUser> =
        inputList.map {
            transform(it) }



}