package net.forevents.foreventsandroid.Data.Model.LoginUser

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