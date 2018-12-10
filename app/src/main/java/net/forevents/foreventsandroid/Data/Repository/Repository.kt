package net.forevents.foreventsandroid.Data.Repository



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.*
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiTransaction
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById
import net.forevents.foreventsandroid.Data.Repository.DataSource.ApiDataSource
import retrofit2.Response
import retrofit2.http.Body


class Repository(private val apiDataSource: ApiDataSource) {

    //########### TRANSACTIONS ###################

    fun postTransaction(token: String,eventId:String):Single<ApiTransaction> =
        apiDataSource.postTransaction(token,eventId)

    fun delTransaction(token: String,transactionId:String):Observable<Response<Body>> =
        apiDataSource.delTransaction(token,transactionId)

    //########### USERS ###################
     fun getUserList():Flowable<List<UserEntity>> =
             apiDataSource.getUserList()

    fun getUserToken(email:String,password:String): Single<AppUser> = apiDataSource.getUserLogin(email,password)
    fun createUser( email: String,
                    password: String,
                    first_name:String,
                    last_name:String,
                    address: String,
                    city: String,
                    zip_code:String,
                    province:String,
                    country:String,
                    alias:String):Single<AppCreateUser> =
        apiDataSource.createUser(email, password, first_name, last_name, address,
                                 city, zip_code, province, country, alias)
    fun recoveryPassword(email:String):Single<OnlyResponse.ResultRecoveryPassword> = apiDataSource.recoveryPassword(email)

    fun deleteProfile(userId:String,token:String):Observable<Response<Body>> = apiDataSource.deleteProfile(userId,token)

    fun updateProfile(userId:String,
                      token:String,
                      email: String,
                      first_name:String,
                      last_name:String,
                      address: String,
                      city: String,
                      zip_code:String,
                      province:String,
                      country:String,
                      alias:String):Observable<OnlyResponse.ResultUpdateProfile> = apiDataSource.updateUser(userId, token, email, first_name, last_name, address, city, zip_code, province, country, alias)

    fun getUserById(userId:String,token:String):Single<AppUserById> = apiDataSource.getUserById(userId,token)


    //########### EVENTS ###################

    fun getEventsList(userId:String):Observable<List<AppEvents>> = apiDataSource.getEvents("name description url",userId)

    //########### EVENTS TYPE ###################

    fun getEventType():Observable<List<AppEventType>> = apiDataSource.getEventType()

    //########### CITY ###################
    fun getCitiesList(city:String,limit:String):Observable<List<AppCity>> = apiDataSource.getCities(city,limit)




     /*fun getUserList(): Flowable<List<UserEntity>> =
          fakeDataSource.getUserList()
          .zipWith(fakeDataSource2.getUserList())
          .map { pair ->
               val newList = mutableListOf<UserEntity>()
               newList.addAll(pair.first)
               newList.addAll(pair.second)
               newList.toList()
          }
          .delay(1,TimeUnit.SECONDS)*/

   /*  fun getUserdetail(userId: Long): Observable<UserEntity> =
          fakeDataSource.getUserdetail(userId)
               //.onErrorResumeNext { _: Throwable -> fakeDataSource2.getUserdetail(userId) }
               .switchIfEmpty(fakeDataSource2.getUserdetail(userId))
               .delay(1,TimeUnit.SECONDS)
*/



}