package net.forevents.foreventsandroid.Data.Repository.DataSource


import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.Model.City.AppCity
import net.forevents.foreventsandroid.Data.Model.CreateUser.AppCreateUser
import net.forevents.foreventsandroid.Data.Model.EventType.AppEventType
import net.forevents.foreventsandroid.Data.Model.Events.AppEvents
import net.forevents.foreventsandroid.Data.Model.LoginUser.AppUser
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiCreateTransaction
import net.forevents.foreventsandroid.Data.Model.Transactions.AppTransactions
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById
import retrofit2.Response
import retrofit2.http.Body


interface DataSource {

    //#####################  TRANSACTIONS   ########################################

    fun postTransaction(token:String,eventId:String):Single<ApiCreateTransaction>

    fun delTransaction(token:String,transactionId:String):Observable<Response<Body>>

    fun getTransactionByUser(token:String):Observable<List<AppTransactions>>


    //#####################  USER   ########################################
    fun getUserList() :Flowable<List<UserEntity>>
    fun getUserLogin(email: String,
                     password: String): Single<AppUser>
    fun deleteProfile(userId:String,token:String):Observable<Response<Body>>

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

    fun getUserById(userId:String,token:String):Single<AppUserById>

    fun updateUser( userId:String,
                    token:String,
                    email: String,
                    first_name:String,
                    last_name:String,
                    address: String,
                    city: String,
                    zip_code:String,
                    province:String,
                    country:String,
                    alias:String):Observable<OnlyResponse.ResultUpdateProfile>

    fun recoveryPassword(email:String):Single<OnlyResponse.ResultRecoveryPassword>

    fun getEvents(media: String, userId:String,eventTypeId:String,location:String):Observable<List<AppEvents>>

    fun getEvent(media:String,eventId:String,userId:String):Single<AppEvents>

    fun getEventType():Observable<List<AppEventType>>

    fun getCities(city:String,limit:String):Observable<List<AppCity>>



}