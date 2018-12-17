package net.forevents.foreventsandroid.Data.Net



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserApiResponse
import net.forevents.foreventsandroid.Data.Model.City.ApiCity
import net.forevents.foreventsandroid.Data.Model.CreateUser.ApiCreateUser
import net.forevents.foreventsandroid.Data.Model.EventType.ApiEventType
import net.forevents.foreventsandroid.Data.Model.Events.ApiEvents
import net.forevents.foreventsandroid.Data.Model.LoginUser.ApiUser
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiCreateTransaction
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiGetTransactions
import net.forevents.foreventsandroid.Data.Model.UserById.ApiUserById

import retrofit2.Response

import retrofit2.http.*

interface UserService {
    //randomuser
    @GET("api/?results=20")
    fun getUsers(): Flowable<UserApiResponse>

    //#####################  TRANSACTIONS   ########################################

    @GET("transactions/list")
    fun getTransactionByUser(@Query("token") token: String):Observable<ApiGetTransactions>

    @FormUrlEncoded
    @POST("transactions")
    fun registerTransaction(@Field("event") event: String,
                            @Query("token") token: String):Single<ApiCreateTransaction>


    @DELETE("transactions/{transactionId}")
    fun deleteTransaction(@Path("transactionId") transactionId:String,
                          @Query("token") token: String):Observable<Response<Body>>



    //@Headers("Content-Type: application/x-www-form-urlencoded")

//#####################  EVENTS   ########################################




    @GET("events")
    fun getListEvents(@Query("media") media: String,
                      @Query("userId") userId:String,
                      @Query("event_typeId") eventTypeId:String,
                      @Query("location") locationRadius:String) : Observable<ApiEvents>
                                            //41.50368,-5.743778,5000
    @GET("events")
    fun getEvent(@Query("media") media: String,
                 @Query("id") eventId:String,
                 @Query("userId") userId:String):Single<ApiEvents>


    @GET("EventTypes")
    fun getEventType() : Observable<ApiEventType>

    @GET("cities")
    fun getCities(@Query("city") city: String,
                 @Query("limit") limit: String) : Observable<ApiCity>


    //#####################  USER   ########################################
    @GET("users/{userId}")
    fun userById(@Path("userId") userId:String,@Query("token") token:String): Single<ApiUserById>


    @FormUrlEncoded
    @PUT("users/{userId}")
    fun updateUser(@Path("userId") userId:String,
                   @Query("token") token:String,
                   @Field("email") email: String,
                   @Field("first_name") first_name:String,
                   @Field("last_name") last_name:String,
                   @Field("address") address: String,
                   @Field("city") city: String,
                   @Field("zip_code") zip_code:String,
                   @Field("province") province:String,
                   @Field("country") country:String,
                   @Field("alias") alias:String
    )    : Observable<OnlyResponse.ResultUpdateProfile>








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



    @DELETE("users/{userId}")
    fun deleteUser(@Path("userId") userId:String,@Query("token") token:String): Observable<Response<Body>>


   // @GET("/user/{username}?type={admin}")
   // void getUserOuth(@Path("username") String username, @Query("admin") String type)
}