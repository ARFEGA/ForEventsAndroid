package net.forevents.foreventsandroid.Data.Net



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.CreateUserApiResponse
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserApiResponse
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse

import retrofit2.http.*

interface UserService {
    //randomuser
    @GET("api/?results=20")
    fun getUsers(): Flowable<UserApiResponse>


    //@Headers("Content-Type: application/x-www-form-urlencoded")

    @FormUrlEncoded
    @POST("users/login")
    fun loginUser(@Field("email") email: String,
                  @Field("password") password: String):Single<ApiUser>

    @FormUrlEncoded
    @POST(" users/recover")
    fun recoveryPassword(@Field("email") email: String):Single<OnlyResponse.ResultRecoveryPassword>



    @FormUrlEncoded
    @POST("users/register")
    fun createUser(@Field("email") email: String,
                   @Field("password") password: String,
                   @Field("first_name") first_name:String,
                   @Field("last_name") last_name:String,
                   @Field("address") address: String,
                   @Field("city") city: String,
                   @Field("zip_code") zip_code:String,
                   @Field("province") province:String,
                   @Field("country") country:String,
                   @Field("alias") alias:String
    )    : Single<ApiCreateUser>


    @GET("events")
    fun getEvent(@Query("media") media: String) : Observable<ApiEvents>

    @GET("EventTypes")
    fun getEventType() : Observable<ApiEventType>

    @GET("cities")
    fun getCities(@Query("city") city: String,
                 @Query("limit") limit: String) : Observable<ApiCity>
}