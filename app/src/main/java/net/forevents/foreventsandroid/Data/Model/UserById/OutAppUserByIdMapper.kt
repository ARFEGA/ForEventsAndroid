package net.forevents.foreventsandroid.Data.CreateUser.User



import net.forevents.foreventsandroid.Data.CreateUser.Mapper
import net.forevents.foreventsandroid.Data.Model.UserById.ApiUserById
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById

class OutAppUserByIdMapper : Mapper<ApiUserById, AppUserById> {
    override fun transform(input: ApiUserById): AppUserById =
        AppUserById(
            input.message,
            input.ok,
            input.user

        )
        //AppUser(input.ok,input.token,input.user._id,input.user.email,input.user.password)

    override fun transformList(inputList: List<ApiUserById>): List<AppUserById> =
        inputList.map {
            transform(it) }



}