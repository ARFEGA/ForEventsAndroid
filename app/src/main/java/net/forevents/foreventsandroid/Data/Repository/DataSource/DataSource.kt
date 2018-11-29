package net.forevents.foreventsandroid.Data.Repository.DataSource


import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.ApiEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCreateUser
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import retrofit2.http.Field


interface DataSource {

    fun getUserList() :Flowable<List<UserEntity>>
    fun getUserLogin(email: String,
                     password: String): Single<AppUser>
    fun createUser( email: String,
                    password: String,
                    first_name:String,
                    last_name:String,
                    address: String,
                    city: String,
                    zip_code:String,
                    province:String,
                    country:String,
                    alias:String):Single<AppCreateUser>




    fun recoveryPassword(email:String):Single<OnlyResponse.ResultRecoveryPassword>

    fun getEvents(media: String):Observable<List<AppEvents>>

}