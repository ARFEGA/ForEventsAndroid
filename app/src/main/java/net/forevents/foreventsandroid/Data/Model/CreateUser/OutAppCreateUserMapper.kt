package net.forevents.foreventsandroid.Data.Model.CreateUser

import net.forevents.foreventsandroid.Data.CreateUser.Mapper

class OutAppCreateUserMapper :
    Mapper<ApiCreateUser, AppCreateUser> {
    override fun transform(input: ApiCreateUser): AppCreateUser =
        AppCreateUser(input.ok,
                        input.message,
                        input.user.id)
        //AppUser(input.ok,input.token,input.user._id,input.user.email,input.user.password)

    override fun transformList(inputList: List<ApiCreateUser>): List<AppCreateUser> =
        inputList.map {
            transform(it) }



}