package net.forevents.foreventsandroid.Data.Repository.DataSource



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppCityMapper


import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppCreateUserMapper
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppEventTypeMapper
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppEventsMapper
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.OutUserEntityMapper
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiTransaction
import net.forevents.foreventsandroid.Data.Model.UserById.ApiUserById
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById
import net.forevents.foreventsandroid.Data.Net.UserService
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppCityMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppCreateUserMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppEventTypeMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppEventsMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppUserByIdMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.outAppUserMapper
import net.forevents.foreventsandroid.presentation.servicelocator.Inject.userService
import retrofit2.Response
import retrofit2.http.Body
import java.io.Serializable
import java.util.concurrent.TimeUnit




class ApiDataSource(private val userService: UserService,
                    private val outUserEntityMapper: OutUserEntityMapper,
                    private val outAppUserMapper: OutAppUserMapper,
                    private val outAppCreateUserMapper: OutAppCreateUserMapper,
                    private val outAppEventsMapper: OutAppEventsMapper,
                    private val outAppCityMapper:OutAppCityMapper,
                    private val outAppEventTypeMapper:OutAppEventTypeMapper,
                    private val outAppUserByIdMapper: OutAppUserByIdMapper
): DataSource {


    //################  TRANSACTIONS  #####################

    override fun delTransaction( token: String,transactionId: String): Observable<Response<Body>> =
        userService.deleteTransaction(transactionId,token)


    override fun postTransaction(token: String, eventId: String): Single<ApiTransaction> =
        userService.registerTransaction(eventId,token)



    //################  USERS  #####################


    override fun updateUser(
        userId: String,
        token: String,
        email: String,
        first_name: String,
        last_name: String,
        address: String,
        city: String,
        zip_code: String,
        province: String,
        country: String,
        alias: String
    ): Observable<OnlyResponse.ResultUpdateProfile> = userService.updateUser( userId, token, email, first_name, last_name, address, city, zip_code, province, country, alias)

    override fun createUser( email: String,
                             password: String,
                             first_name:String,
                             last_name:String,
                             address: String,
                             city: String,
                             zip_code:String,
                             province:String,
                             country:String,
                             alias:String): Single<AppCreateUser> =
        userService.createUser(email, password, first_name, last_name, address, city, zip_code, province, country, alias)
            .map{
                outAppCreateUserMapper.transform(it)
            }

    override fun getUserLogin(email: String,password: String): Single<AppUser> =
        userService.loginUser(email, password)
            //.map { it.results }
            .map { outAppUserMapper.transform(it) }

    override fun getUserList(): Flowable<List<UserEntity>> =
         userService.getUsers().map {
            it.results
        }
            .map { Inject.outUserEntityMapper.transformList(it) }
            .delay(2,TimeUnit.SECONDS)
    override fun recoveryPassword(email:String): Single<OnlyResponse.ResultRecoveryPassword> = userService.recoveryPassword(email)

    override fun deleteProfile(userId: String, token: String): Observable<Response<Body>> = userService.deleteUser(userId,token)

    override fun getUserById(userId: String, token: String): Single<AppUserById> =
        userService.userById(userId,token)
            .map { outAppUserByIdMapper.transform(it) }



    //################  EVENTS  #####################

    override fun getEvents(media:String,userId:String): Observable<List<AppEvents>> =
            userService.getEvent(media,userId)
                .map{
                outAppEventsMapper.transformList(it.result)
            }

    //################  EVENT TYPE  #####################

    override fun getEventType(): Observable<List<AppEventType>> =
        userService.getEventType()
            .map {
                outAppEventTypeMapper.transformList(it.result)
            }

    //################   CITY   #####################

    override fun getCities(city: String, limit: String): Observable<List<AppCity>> =
        userService.getCities(city,limit)
            .map {
            outAppCityMapper.transformList(it.result)
        }




}





