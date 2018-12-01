package net.forevents.foreventsandroid.Data.Repository.DataSource



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppCityMapper


import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppCreateUserMapper
import net.forevents.foreventsandroid.Data.CreateUser.CreateUser.OutAppEventsMapper
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.OutUserEntityMapper
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Net.UserService
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import java.io.Serializable
import java.util.concurrent.TimeUnit




class ApiDataSource(private val userService: UserService,
                    private val outUserEntityMapper: OutUserEntityMapper,
                    private val outAppUserMapper: OutAppUserMapper,
                    private val outAppCreateUserMapper: OutAppCreateUserMapper,
                    private val outAppEventsMapper: OutAppEventsMapper,
                    private val outAppCityMapper:OutAppCityMapper
): DataSource {


    //################  USERS  #####################

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

    override fun getUserLogin(email: String,
                              password: String): Single<AppUser> =
        userService.loginUser(email, password)
            //.map { it.results }
            .map { outAppUserMapper.transform(it) }

    override fun getUserList(): Flowable<List<UserEntity>> =
         userService.getUsers().map {
            it.results
        }
            .map { Inject.outUserEntityMapper.transformList(it) }
            .delay(2,TimeUnit.SECONDS)
    override fun recoveryPassword(email:String): Single<OnlyResponse.ResultRecoveryPassword> {
        return userService.recoveryPassword(email)
    }


    //################  EVENTS  #####################

    override fun getEvents(media:String): Observable<List<AppEvents>> =
            userService.getEvent(media)
                .map{
                outAppEventsMapper.transformList(it.result)
            }
    //################   CITY   #####################

    override fun getCities(city: String, limit: String): Observable<List<AppCity>> =
        userService.getCities(city,limit)
            .map {
            outAppCityMapper.transformList(it.result)
        }




}





