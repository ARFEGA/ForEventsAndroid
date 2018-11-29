package net.forevents.foreventsandroid.Data.Repository



import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCreateUser
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Repository.DataSource.ApiDataSource


class Repository(private val apiDataSource: ApiDataSource) {

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

    //########### EVENTS ###################

    fun getEventsList():Observable<List<AppEvents>> = apiDataSource.getEvents("name description url")

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